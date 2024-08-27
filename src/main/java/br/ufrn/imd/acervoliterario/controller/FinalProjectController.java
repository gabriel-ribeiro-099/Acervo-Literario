package br.ufrn.imd.acervoliterario.controller;

import br.ufrn.imd.acervoliterario.dto.ApiResponseDTO;
import br.ufrn.imd.acervoliterario.dto.EntityDTO;
import br.ufrn.imd.acervoliterario.dto.FinalProjectDTO;
import br.ufrn.imd.acervoliterario.mapper.FinalProjectMapper;
import br.ufrn.imd.acervoliterario.model.FinalProject;
import br.ufrn.imd.acervoliterario.service.FinalProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar TCCs (FinalProject).
 * Fornece endpoints para criar, atualizar, excluir e buscar Projetos Finais.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/finalProject")
public class FinalProjectController extends GenericController<FinalProject, FinalProjectDTO, FinalProjectService> {

    private FinalProjectMapper finalProjectMapper;

    /**
     * Construtor para injetar o serviço de TCC e o mapeador.
     *
     * @param finalProjectService Serviço para manipulação de TCCs.
     * @param finalProjectMapper  Mapeador para converter entre entidades e DTOs.
     */
    public FinalProjectController(FinalProjectService finalProjectService, FinalProjectMapper finalProjectMapper) {
        super(finalProjectService);
        this.finalProjectMapper = finalProjectMapper;
    }

    /**
     * Endpoint para criar um novo TCC.
     *
     * @param dto     Dados do TCC a ser criado.
     * @param request Requisição HTTP.
     * @return ResponseEntity com a resposta da API.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<EntityDTO>> createFinalProject(
            @Valid @RequestBody FinalProjectDTO dto,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO<>(
                        true,
                        "Success: Final project created successfully.",
                        service.create(dto, request).toResponse(),
                        null
                )
        );
    }

    /**
     * Endpoint para atualizar um TCC existente.
     *
     * @param id      Identificador do TCC a ser atualizado.
     * @param dto     Dados atualizados do TCC.
     * @param request Requisição HTTP.
     * @return ResponseEntity com a resposta da API.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponseDTO<FinalProjectDTO>> updateFinalProject(
            @PathVariable long id,
            @Valid @RequestBody FinalProjectDTO dto,
            HttpServletRequest request) {

        FinalProject finalProjectEntity = finalProjectMapper.toEntity(dto);
        FinalProject updatedFinalProject = service.updateById(id, finalProjectEntity, request);
        FinalProjectDTO updatedDTO = finalProjectMapper.toDto(updatedFinalProject);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDTO<>(
                        true,
                        "Success: Final Project updated successfully.",
                        updatedDTO,
                        null
                )
        );
    }

    /**
     * Endpoint para excluir um TCC.
     *
     * @param id      Identificador do TCC a ser excluído.
     * @param request Requisição HTTP.
     * @return ResponseEntity com a resposta da API.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<FinalProjectDTO>> deleteFinalProject(@PathVariable long id, HttpServletRequest request) {
        service.deleteById(id, request);
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Final Project has been successfully removed.",
                null,
                null));
    }

    /**
     * Endpoint para buscar um TCC por seu identificador.
     *
     * @param id Identificador do TCC.
     * @return ResponseEntity com a resposta da API contendo o TCC encontrado.
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponseDTO<FinalProjectDTO>> findFinalProjectById(@PathVariable long id) {
        FinalProjectDTO finalProjectDTO = service.findById(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Final Project retrieved successfully.",
                finalProjectDTO,
                null
        ));
    }

    /**
     * Endpoint para buscar TCCs por nome.
     *
     * @param nome Nome do TCC.
     * @return ResponseEntity com a resposta da API contendo a lista de TCCs encontrados.
     */
    @GetMapping("/find-by-name/{nome}")
    public ResponseEntity<ApiResponseDTO<List<FinalProjectDTO>>> findFinalProjectByName(
            @PathVariable String nome) {

        List<FinalProjectDTO> finalProject = service.findByName(nome);

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Final Project retrieved successfully.",
                finalProject,
                null
        ));
    }

    /**
     * Endpoint para buscar todos os TCCs com paginação.
     *
     * @param pageable Especifica paginação e ordenação.
     * @return ResponseEntity contendo uma página de TCCs.
     */
    @GetMapping("/find-all")
    public ResponseEntity<ApiResponseDTO<Page<FinalProjectDTO>>> findAllFinalProject(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Final Project retrieved successfully.",
                service.findAll(pageable),
                null
        ));
    }

}
