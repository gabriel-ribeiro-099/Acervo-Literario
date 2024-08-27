package br.ufrn.imd.acervoliterario.service;

import br.ufrn.imd.acervoliterario.dto.EntityDTO;
import br.ufrn.imd.acervoliterario.model.BaseEntity;
import br.ufrn.imd.acervoliterario.repository.GenericRepository;
import br.ufrn.imd.acervoliterario.mapper.DtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.ufrn.imd.acervoliterario.utils.exception.ResourceNotFoundException;

/**
 * Interface genérica para serviços que lidam com entidades e DTOs.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 *
 * @param <E>   Tipo da entidade que estende BaseEntity.
 * @param <DTO> Tipo do DTO que estende EntityDTO.
 */
@Service
public interface GenericService<E extends BaseEntity, DTO extends EntityDTO> {

    /**
     * Retorna o repositório específico da entidade.
     *
     * @return Repositório da entidade.
     */
    GenericRepository<E> getRepository();

    /**
     * Retorna o mapeador que converte entre entidade e DTO.
     *
     * @return Mapeador da entidade e DTO.
     */
    DtoMapper<E, DTO> getDtoMapper();

    /**
     * Retorna uma página de DTOs com base nas entidades paginadas.
     *
     * @param pageable Informações de paginação.
     * @return Página de DTOs.
     */
    default Page<DTO> findAll(Pageable pageable) {
        Page<E> entityPage = getRepository().findAll(pageable);
        return new PageImpl<>(getDtoMapper().toDto(entityPage.getContent()), pageable, entityPage.getTotalElements());
    }

    /**
     * Busca uma entidade pelo seu ID e a converte em DTO.
     *
     * @param id ID da entidade.
     * @return DTO correspondente à entidade encontrada.
     * @throws ResourceNotFoundException Se a entidade com o ID especificado não for encontrada.
     */
    default DTO findById(Long id) {
        E entity = getRepository().findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found: " + id));
        return getDtoMapper().toDto(entity);
    }

    /**
     * Cria uma nova entidade a partir de um DTO e a salva no banco de dados.
     *
     * @param dto DTO com os dados para criar a entidade.
     * @return DTO da entidade recém-criada.
     */
    default DTO create(DTO dto) {
        E entity = getDtoMapper().toEntity(dto);
        validateBeforeSave(entity);
        return getDtoMapper().toDto(getRepository().save(entity));
    }

    /**
     * Atualiza uma entidade existente com base em seu ID e nos dados fornecidos no DTO.
     *
     * @param id  ID da entidade a ser atualizada.
     * @param dto DTO com os novos dados para a entidade.
     * @return DTO da entidade atualizada.
     */
    default DTO update(Long id, DTO dto) {
        E updatedEntity = getDtoMapper().toEntity(dto);
        updatedEntity.setId(id);
        validateBeforeUpdate(updatedEntity);
        return getDtoMapper().toDto(getRepository().save(updatedEntity));
    }

    /**
     * Exclui uma entidade com base em seu ID.
     *
     * @param id ID da entidade a ser excluída.
     */
    default void deleteById(Long id) {
        getRepository().deleteById(id);
    }

    /**
     * Valida uma entidade antes de salvá-la no banco de dados.
     * Pode ser sobreposto para adicionar lógica de validação específica.
     *
     * @param entity Entidade a ser validada.
     */
    default void validateBeforeSave(E entity) {
    }

    /**
     * Valida uma entidade antes de atualizá-la no banco de dados.
     * Pode ser sobreposto para adicionar lógica de validação específica.
     *
     * @param entity Entidade a ser validada.
     */
    default void validateBeforeUpdate(E entity) {
    }
}
