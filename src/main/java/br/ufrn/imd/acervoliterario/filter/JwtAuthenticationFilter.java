package br.ufrn.imd.acervoliterario.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import br.ufrn.imd.acervoliterario.service.JwtService;
import br.ufrn.imd.acervoliterario.service.UserDetailsServiceImpl;

import java.io.IOException;

/**
 * Filtro de autenticação JWT que intercepta todas as requisições HTTP e verifica a presença de um token JWT.
 * <p>
 * O filtro é executado uma vez por requisição e verifica se o token JWT fornecido é válido. Se o token for
 * válido, a autenticação do usuário é configurada no contexto de segurança do Spring.
 * </p>
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Construtor para JwtAuthenticationFilter.
     *
     * @param jwtService          Serviço responsável por manipular o JWT.
     * @param userDetailsService  Serviço responsável por carregar os detalhes do usuário.
     */
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filtra as requisições HTTP para verificar a validade do token JWT.
     * <p>
     * Se o token JWT for válido, configura a autenticação do usuário no contexto de segurança.
     * </p>
     *
     * @param request     A requisição HTTP.
     * @param response    A resposta HTTP.
     * @param filterChain O chain de filtros.
     * @throws ServletException Se ocorrer um erro de servlet.
     * @throws IOException      Se ocorrer um erro de I/O.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove o prefixo "Bearer "
            String username = jwtService.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
