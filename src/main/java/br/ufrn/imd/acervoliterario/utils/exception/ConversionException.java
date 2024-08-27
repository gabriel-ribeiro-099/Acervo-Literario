package br.ufrn.imd.acervoliterario.utils.exception;

/**
 * Exceção lançada quando ocorre um problema durante a conversão de dados.
 * Estende RuntimeException para indicar uma exceção não verificada (unchecked).
 *
 * Esta exceção é utilizada para sinalizar falhas específicas relacionadas à conversão
 * de dados dentro da aplicação.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
public class ConversionException extends RuntimeException {

    /**
     * Constrói uma nova instância de ConversionException com a mensagem detalhada fornecida.
     *
     * @param message Mensagem detalhada que descreve o erro de conversão.
     */
    public ConversionException(String message) {
        super(message);
    }
}
