package br.ufrn.imd.acervoliterario.dto;

/**
 * Classe utilizada para transportar os dados de login e senha fornecidos pelo usuário
 * para o serviço de autenticação.
 *
 * @author Nicole Nogueira
 * @version 1.0
 *
 * @param login    O nome de usuário ou identificador utilizado para autenticação.
 * @param password A senha associada ao usuário.
 */
public record AuthRequest(String login, String password) {
}

