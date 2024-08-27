package br.ufrn.imd.acervoliterario.utils.exception;

import org.springframework.http.HttpStatus;

/**
 * Exceção personalizada que representa um erro de lógica de negócios na aplicação.
 * Estende RuntimeException para indicar uma exceção não verificada (unchecked).
 *
 * Sinaliza problemas específicos de negócios e pode incluir um código
 * de status HTTP para fornecer informações adicionais sobre o erro.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatusCode;

    /**
     * Constrói uma nova instância de BusinessException com a mensagem detalhada e o código
     * de status HTTP especificados.
     *
     * @param message    Mensagem detalhada que descreve o erro.
     * @param statusCode Código de status HTTP associado ao erro.
     */
    public BusinessException(String message, HttpStatus statusCode) {
        super(message);
        this.httpStatusCode = statusCode;
    }

    /**
     * Retorna o código de status HTTP associado a esta exceção.
     *
     * @return Código de status HTTP.
     */
    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}
