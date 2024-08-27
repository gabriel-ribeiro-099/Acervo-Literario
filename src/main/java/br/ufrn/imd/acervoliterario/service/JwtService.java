package br.ufrn.imd.acervoliterario.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import br.ufrn.imd.acervoliterario.model.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Serviço responsável pela manipulação de tokens JWT, como geração, validação
 * e extração de informações.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@Service
public class JwtService {

    /**
     * Chave secreta usada para assinar os tokens JWT.
     * Esta chave é injetada via configuração.
     */
    @Value("${secret.key}")
    private String SECRET_KEY;

    /**
     * Verifica se o token JWT é válido para o usuário fornecido.
     *
     * @param token Token JWT.
     * @param user  Usuário para o qual o token deve ser validado.
     * @return True se o token for válido, False caso contrário.
     */
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Verifica se o token JWT já expirou.
     *
     * @param token Token JWT.
     * @return True se o token expirou, False caso contrário.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrai a data de expiração do token JWT.
     *
     * @param token Token JWT.
     * @return Data de expiração do token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrai o nome de usuário (login) do token JWT.
     *
     * @param token Token JWT.
     * @return Nome de usuário contido no token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai um claim específico do token JWT usando uma função resolvente.
     *
     * @param token    Token JWT.
     * @param resolver Função que resolve o claim a ser extraído.
     * @param <T>      Tipo do claim extraído.
     * @return Claim extraído.
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Extrai todos os claims do token JWT.
     *
     * @param token Token JWT.
     * @return Todos os claims contidos no token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Gera um token JWT para um usuário específico, incluindo informações extras.
     *
     * @param user Usuário para o qual o token será gerado.
     * @return Token JWT gerado.
     */
    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", String.valueOf(user.getId()));

        SecretKey key = getSigninKey();

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getLogin())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hora
                .signWith(key)
                .compact();
    }

    /**
     * Extrai o ID do usuário do token JWT.
     *
     * @param token Token JWT.
     * @return ID do usuário extraído do token.
     */
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return Long.parseLong(claims.get("id", String.class));
    }

    /**
     * Retorna a chave secreta usada para assinar os tokens JWT.
     *
     * @return Chave secreta.
     */
    private SecretKey getSigninKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        System.out.println("Generated Secret Key: " + Base64.getEncoder().encodeToString(key.getEncoded()));

        return key;
    }
}
