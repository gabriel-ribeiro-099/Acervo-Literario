package br.ufrn.imd.acervoliterario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import br.ufrn.imd.acervoliterario.filter.JwtAuthenticationFilter;
import br.ufrn.imd.acervoliterario.service.UserDetailsServiceImpl;

/**
 * Classe de configuração de segurança para a aplicação.
 * Configura a autenticação e a autorização, incluindo o filtro JWT e a codificação de senhas.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Construtor da classe SecurityConfig.
     *
     * @param userDetailsServiceImpl Serviço que implementa a lógica de autenticação e carregamento de usuários.
     * @param jwtAuthenticationFilter Filtro responsável por verificar e validar tokens JWT.
     */
    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configura a cadeia de filtros de segurança da aplicação.
     * Define as permissões de acesso, a política de sessão e adiciona o filtro JWT.
     *
     * @param http Objeto HttpSecurity usado para configurar a segurança HTTP.
     * @return Objeto SecurityFilterChain configurado.
     * @throws Exception Se ocorrer um erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/v1/auth/authenticate", "/v1/users/register")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImpl)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configura o codificador de senhas a ser usado na aplicação.
     *
     * @return Objeto BCryptPasswordEncoder que será utilizado para codificar senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura o gerenciador de autenticação da aplicação.
     *
     * @param configuration Objeto AuthenticationConfiguration utilizado para configurar o AuthenticationManager.
     * @return Objeto AuthenticationManager configurado.
     * @throws Exception Se ocorrer um erro durante a configuração.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}

