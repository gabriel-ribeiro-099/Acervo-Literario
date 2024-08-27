package br.ufrn.imd.acervoliterario.dto;

/**
 * Classe utilizada para transportar o token JWT gerado após uma autenticação bem-sucedida.
 *
 * @author Nicole Nogueira
 * @version 1.0
 *
 * @param token O token JWT gerado para autenticação do usuário.
 */
public record AuthResponse(String token) {
}
