package br.ufrn.imd.acervoliterario.utils.exception;

import java.time.ZonedDateTime;

/**
 * Data Transfer Object (DTO) que representa as respostas de erro na aplicação.
 * Este DTO encapsula detalhes sobre um erro, incluindo timestamp, status, tipo de erro,
 * mensagem e caminho da requisição.
 * Este registro é imutável e serve para fornecer uma estrutura padronizada de resposta
 * de erro.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 *
 * @param timestamp A data e hora em que o erro ocorreu (ZonedDateTime).
 * @param status    O código de status HTTP associado ao erro.
 * @param error     O tipo ou categoria do erro.
 * @param message   Uma mensagem detalhada descrevendo o problema.
 * @param path      O caminho URI que acionou o erro.
 */
public record ErrorDTO(ZonedDateTime timestamp, Integer status, String error, String message, String path) {
}
