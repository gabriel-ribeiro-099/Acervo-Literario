package br.ufrn.imd.acervoliterario.mapper;

import java.util.List;

/**
 * Interface que define operações de mapeamento entre objetos de entidade (E) e DTOs (DTO).
 * Fornece métodos para converter entidades em DTOs e vice-versa.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 * @param <E>   O tipo da entidade.
 * @param <DTO> O tipo do DTO.
 */

public interface DtoMapper<E, DTO> {

    /**
     * Converte uma entidade para seu respectivo DTO.
     *
     * @param entity Entidade a ser convertida.
     * @return DTO que representa a entidade.
     */

    DTO toDto(E entity);

    /**
     * Converte uma lista de entidades para uma lista de DTOs.
     *
     * @param entity Lista de entidades a ser convertida.
     * @return Lista de DTOs que representam as entidades.
     */

    default List<DTO> toDto(List<E> entity) {
        return entity.stream().map(this::toDto).toList();
    }

    /**
     * Converte um DTO para sua respectiva entidade.
     *
     * @param dto DTO a ser convertido.
     * @return Entidade que representa o DTO.
     */

    E toEntity(DTO dto);

}
