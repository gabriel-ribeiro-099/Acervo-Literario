package br.ufrn.imd.acervoliterario.utils.exception;

/**
 * Exceção lançada quando as credenciais fornecidas são inválidas.
 * Esta exceção estende RuntimeException para indicar que é uma exceção não verificada.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Constrói uma nova InvalidCredentialsException com a mensagem detalhada especificada.
     *
     * @param message Mensagem detalhada que descreve o motivo da exceção.
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
