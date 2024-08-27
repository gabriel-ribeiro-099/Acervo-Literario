package br.ufrn.imd.acervoliterario.utils.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import br.ufrn.imd.acervoliterario.dto.ApiResponseDTO;
import br.ufrn.imd.acervoliterario.utils.exception.BusinessException;
import br.ufrn.imd.acervoliterario.utils.exception.ConversionException;
import br.ufrn.imd.acervoliterario.utils.exception.ErrorDTO;
import br.ufrn.imd.acervoliterario.utils.exception.ResourceNotFoundException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Manipulador global de exceções para os controladores da aplicação.
 * Trata vários tipos de exceções e as mapeia para respostas de erro apropriadas.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    private static final String MSG_ERRO = "Erro: ";

    /**
     * Trata a BusinessException e mapeia para uma resposta de erro personalizada.
     *
     * @param exception Instância de BusinessException.
     * @param request   HttpServletRequest.
     * @return ResponseEntity contendo a resposta de erro.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> businessException(BusinessException exception,
                                                                      HttpServletRequest request) {

        var err = new ErrorDTO(
                ZonedDateTime.now(),
                exception.getHttpStatusCode().value(),
                "Business error",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<ErrorDTO>(
                false,
                MSG_ERRO + exception.getMessage(),
                null,
                err));
    }

    /**
     * Trata a ResourceNotFoundException e mapeia para uma resposta de erro personalizada.
     *
     * @param exception Instância de ResourceNotFoundException.
     * @param request   HttpServletRequest.
     * @return ResponseEntity contendo a resposta de erro.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> notFound(ResourceNotFoundException exception,
                                                             HttpServletRequest request) {

        var err = new ErrorDTO(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<ErrorDTO>(
                false,
                MSG_ERRO + exception.getMessage(),
                null,
                err));
    }

    /**
     * Trata a ConversionException e mapeia para uma resposta de erro personalizada.
     *
     * @param exception Instância de ConversionException.
     * @param request   HttpServletRequest.
     * @return ResponseEntity contendo a resposta de erro.
     */
    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> conversionException(ConversionException exception,
                                                                        HttpServletRequest request) {

        var err = new ErrorDTO(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected problem occurred while converting data.",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<ErrorDTO>(
                false,
                MSG_ERRO + exception.getMessage(),
                null,
                err));

    }

    /**
     * Trata a TransactionSystemException e mapeia para uma resposta de erro personalizada.
     *
     * @param ex      Instância de TransactionSystemException.
     * @param request HttpServletRequest.
     * @return ResponseEntity contendo a resposta de erro.
     */
    @ExceptionHandler({ TransactionSystemException.class })
    protected ResponseEntity<ApiResponseDTO<ErrorDTO>> handlePersistenceException(Exception ex,
                                                                                  HttpServletRequest request) {
        logger.info(ex.getClass().getName());

        Throwable cause = ((TransactionSystemException) ex).getRootCause();
        if (cause instanceof ConstraintViolationException consEx) {
            final List<String> errors = new ArrayList<>();
            for (final ConstraintViolation<?> violation : consEx.getConstraintViolations()) {
                errors.add(violation.getPropertyPath() + ": " + violation.getMessage());
            }

            final var err = new ErrorDTO(
                    ZonedDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    "Erro ao salvar dados.",
                    errors.toString(),
                    request.getRequestURI());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO<ErrorDTO>(
                    false,
                    MSG_ERRO + ex.getMessage(),
                    null,
                    err));
        }
        return internalErrorException(ex, request);
    }

    /**
     * Trata qualquer outra exceção inesperada e mapeia para uma resposta de erro genérica.
     *
     * @param e       Instância de Exception inesperada.
     * @param request HttpServletRequest.
     * @return ResponseEntity contendo a resposta de erro.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<ErrorDTO>> internalErrorException(Exception e, HttpServletRequest request) {

        var err = new ErrorDTO(
                ZonedDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected problem occurred.",
                e.getMessage(),
                request.getRequestURI());

        logger.error("An unexpected problem occurred. ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<ErrorDTO>(
                false,
                MSG_ERRO + e.getMessage(),
                null,
                err));
    }
}
