package br.ufrn.imd.acervoliterario.utils.exception;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado.
 * Esta exceção estende RuntimeException para indicar que é uma exceção não verificada.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constrói uma nova ResourceNotFoundException com a mensagem detalhada especificada.
     *
     * @param message Mensagem detalhada que descreve o motivo da exceção.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
