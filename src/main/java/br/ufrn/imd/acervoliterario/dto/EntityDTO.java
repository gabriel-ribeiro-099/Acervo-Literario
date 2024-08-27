package br.ufrn.imd.acervoliterario.dto;

/**
 * Interface que define o comportamento de um Data Transfer Object (DTO) no sistema.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
public interface EntityDTO {

    /**
     * Converte o DTO em sua representação de resposta.
     *
     * @return Um objeto do tipo {@code EntityDTO} representando a resposta.
     */
    EntityDTO toResponse();
}
