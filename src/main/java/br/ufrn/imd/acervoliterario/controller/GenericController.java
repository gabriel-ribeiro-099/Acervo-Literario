package br.ufrn.imd.acervoliterario.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import br.ufrn.imd.acervoliterario.dto.EntityDTO;
import br.ufrn.imd.acervoliterario.model.BaseEntity;
import br.ufrn.imd.acervoliterario.service.GenericService;
import br.ufrn.imd.acervoliterario.dto.ApiResponseDTO;

/**
 * Um controlador genérico que fornece operações comuns de CRUD para entidades na aplicação.
 * Esta classe abstrata define métodos para lidar com requisições HTTP relacionadas à
 * gestão de entidades.
 *
 * @param <E>   O tipo da entidade que estende BaseEntity.
 * @param <DTO> O tipo do DTO (Data Transfer Object) associado à entidade.
 * @param <S>   O tipo do serviço que estende GenericService para a entidade.
 * @author Gabriel Ribeiro
 * @version 1.0
 */

@Validated
public abstract class GenericController<E extends BaseEntity, DTO extends EntityDTO, S extends GenericService<E, DTO>> {

    protected S service;

    /**
     * Constrói uma instância GenericController com o serviço fornecido.     *
     * @param service Serviço associado ao controller.
     */
    protected GenericController(S service) {
        this.service = service;
    }

    /**
     * Obtém todas as entidades.
     *
     * @return ResponseEntity contendo a lista de DTOs.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<PageImpl<EntityDTO>>> getAll(Pageable pageable) {
        var page = service.findAll(pageable);
        var res = new PageImpl<>(page.getContent().stream().map(DTO::toResponse).toList(), pageable,
                page.getTotalElements());

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Sucess: Entity located successfully.",
                res,
                null));
    }

    /**
     * Obtém uma entidade pelo seu id.
     *
     * @param id Id da entidade a ser obtida.
     * @return ResponseEntity contendo DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<EntityDTO>> getById(@PathVariable long id) {
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Sucess: Entity has been successfully obtained.",
                service.findById(id).toResponse(),
                null));
    }

    /**
     * Salva uma nova entidade.
     *
     * @param dto DTO que representa a entidade a ser salva.
     * @return ResponseEntity contendo o DTO.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<EntityDTO>> create(@Valid @RequestBody DTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(
                true,
                "Sucess: Entity created successfully..",
                service.create(dto).toResponse(),
                null));
    }

    /**
     * Atualiza uma entidade já existente.
     *
     * @param id  Id da identidade a ser atulizada.
     * @param dto DTO que representa a entidade a ser atualizada.
     * @return ResponseEntity contendo o DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<EntityDTO>> update(@PathVariable long id, @Valid @RequestBody DTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO<>(
                true,
                "Sucess: Entity has been successfully updated.",
                service.update(id, dto).toResponse(),
                null));
    }

    /**
     * Exclui uma entidade pelo seu ID.
     *
     * @param id Id da entidade a ser excluída.
     * @return ResponseEntity com status 200 (OK).
     */

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponseDTO<DTO>> delete(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Sucess: Entity has been successfully removed.",
                null,
                null));
    }
}

