package br.ufrn.imd.acervoliterario.utils.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import br.ufrn.imd.acervoliterario.utils.exception.BusinessException;

import java.util.Set;

/**
 * Classe utilitária para validar entidades usando Bean Validation.
 * Fornece um método estático para validar objetos com base nas
 * restrições definidas.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
public class GenericEntityValidator {

    /**
     * Construtor privado para evitar a instância direta da classe utilitária.
     * Esta classe deve ser utilizada através dos seus métodos estáticos.
     */
    private GenericEntityValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Valida um objeto genérico usando Bean Validation.
     *
     * @param object Objeto a ser validado.
     * @param <T>    Tipo do objeto a ser validado.
     * @throws BusinessException Se a validação falhar, contendo mensagens de erro e
     *                           status HTTP.
     */
    public static <T> void validate(T object) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Erro de validação: ");
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(" ").append(violation.getMessage());
            }
            throw new BusinessException(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
