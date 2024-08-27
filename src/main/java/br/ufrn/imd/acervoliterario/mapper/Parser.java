package br.ufrn.imd.acervoliterario.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.ufrn.imd.acervoliterario.utils.exception.ConversionException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Classe utilitária para converter um objeto (source) em outro objeto (target)
 * usando reflexão.
 * Fornece um método estático para analisar e copiar campos com nomes e tipos correspondentes
 * do objeto de origem para o objeto de destino.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */

public class Parser {

    private Parser() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    /**
     * Analisa o objeto de origem e cria uma nova instância da classe de destino,
     * copiando os campos correspondentes.
     *
     * @param <T>         O tipo do objeto de origem.
     * @param <U>         O tipo do objeto de destino.
     * @param source      O objeto de origem a ser analisado.
     * @param targetClass A classe do objeto de destino.
     * @return O objeto de destino com os campos copiados.
     * @throws ConversionException se houver um erro durante o processo de conversão.
     */

    public static <T, U> U parse(T source, Class<U> targetClass) throws ConversionException {
        U target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {

            logger.error(e.getMessage());
            throw new ConversionException("Erro ao converter dado");
        }

        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        for (Field sourceField : sourceFields) {
            for (Field targetField : targetFields) {
                if (sourceField.getName().equals(targetField.getName()) &&
                        sourceField.getType().equals(targetField.getType())) {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);

                    try {
                        targetField.set(target, sourceField.get(source));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage());
                        throw new ConversionException("Erro ao converter dado");
                    }
                }
            }
        }
        return target;
    }
}

