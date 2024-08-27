package br.ufrn.imd.acervoliterario.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.ufrn.imd.acervoliterario.dto.AuthRequest;
import br.ufrn.imd.acervoliterario.dto.AuthResponse;
import br.ufrn.imd.acervoliterario.model.User;
import br.ufrn.imd.acervoliterario.repository.UserRepository;
import br.ufrn.imd.acervoliterario.utils.exception.ResourceNotFoundException;

/**
 * Serviço responsável pela autenticação de usuários.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Construtor da classe AuthenticationService.
     *
     * @param userRepository o repositório de usuários.
     * @param passwordEncoder o codificador de senhas.
     * @param jwtService o serviço para manipulação de tokens JWT.
     * @param authenticationManager o gerenciador de autenticação.
     */
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Autentica um usuário com base nas credenciais fornecidas.
     *
     * @param request Objeto contendo as credenciais de autenticação.
     * @return Resposta contendo o token JWT se a autenticação for bem-sucedida.
     * @throws BadCredentialsException se as credenciais forem inválidas.
     * @throws RuntimeException se ocorrer um erro inesperado durante a autenticação.
     */
    public AuthResponse authenticate(AuthRequest request) {
        try {
            // Autentica o usuário usando o AuthenticationManager do Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.login(), request.password()
                    )
            );

            // Busca o usuário pelo login
            User user = userRepository.findByLogin(request.login())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            // Gera um token JWT para o usuário autenticado
            String token = jwtService.generateToken(user);

            // Retorna a resposta de autenticação contendo o token JWT
            return new AuthResponse(token);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred during authentication", e);
        }
    }
}
