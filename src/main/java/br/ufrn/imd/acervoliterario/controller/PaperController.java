package br.ufrn.imd.acervoliterario.controller;

import br.ufrn.imd.acervoliterario.dto.ApiResponseDTO;
import br.ufrn.imd.acervoliterario.dto.EntityDTO;
import br.ufrn.imd.acervoliterario.dto.PaperDTO;
import br.ufrn.imd.acervoliterario.mapper.PaperMapper;
import br.ufrn.imd.acervoliterario.model.Paper;
import br.ufrn.imd.acervoliterario.service.PaperService;
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
 * Controlador REST para gerenciar artigos acadêmicos (Paper).
 * Fornece endpoints para criar, atualizar, excluir e buscar artigos.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/paper")
public class PaperController extends GenericController<Paper, PaperDTO, PaperService> {

    private PaperMapper paperMapper;

    /**
     * Construtor para injetar o serviço de Paper e o mapeador.
     *
     * @param paperService Serviço para manipulação de artigos.
     * @param paperMapper  Mapeador para converter entre entidades e DTOs.
     */
    public PaperController(PaperService paperService, PaperMapper paperMapper) {
        super(paperService);
        this.paperMapper = paperMapper;
    }

    /**
     * Endpoint para criar um novo artigo.
     *
     * @param dto     Dados do artigo a ser criado.
     * @param request Requisição HTTP.
     * @return ResponseEntity com a resposta da API.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<EntityDTO>> createPaper(
            @Valid @RequestBody PaperDTO dto,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO<>(
                        true,
                        "Success: Paper created successfully.",
                        service.create(dto, request).toResponse(),
                        null
                )
        );
    }

    /**
     * Endpoint para atualizar um artigo existente.
     *
     * @param id      Identificador do artigo a ser atualizado.
     * @param dto     Dados atualizados do artigo.
     * @param request Requisição HTTP.
     * @return ResponseEntity com a resposta da API.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponseDTO<PaperDTO>> updatePaper(
            @PathVariable long id,
            @Valid @RequestBody PaperDTO dto,
            HttpServletRequest request) {

        Paper paperEntity = paperMapper.toEntity(dto);
        Paper updatedPaper = service.updateById(id, paperEntity, request);
        PaperDTO updatedDTO = paperMapper.toDto(updatedPaper);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDTO<>(
                        true,
                        "Success: Paper updated successfully.",
                        updatedDTO,
                        null
                )
        );
    }

    /**
     * Endpoint para excluir um artigo.
     *
     * @param id      Identificador do artigo a ser excluído.
     * @param request Requisição HTTP.
     * @return ResponseEntity com a resposta da API.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<PaperDTO>> deletePaper(@PathVariable long id, HttpServletRequest request) {
        service.deleteById(id, request);
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Paper has been successfully removed.",
                null,
                null));
    }

    /**
     * Endpoint para buscar um artigo por seu identificador.
     *
     * @param id Identificador do artigo.
     * @return ResponseEntity com a resposta da API contendo o artigo encontrado.
     */
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<ApiResponseDTO<PaperDTO>> findPaperById(@PathVariable long id) {
        PaperDTO paperDTO = service.findById(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Paper retrieved successfully.",
                paperDTO,
                null
        ));
    }

    /**
     * Endpoint para buscar artigos por nome.
     *
     * @param nome Nome do artigo.
     * @return ResponseEntity com a resposta da API contendo a lista de artigos encontrados.
     */
    @GetMapping("/find-by-name/{nome}")
    public ResponseEntity<ApiResponseDTO<List<PaperDTO>>> findPaperByName(
            @PathVariable String nome) {

        List<PaperDTO> paper = service.findByName(nome);

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Paper retrieved successfully.",
                paper,
                null
        ));
    }

    /**
     * Endpoint para buscar todos os artigos com paginação.
     *
     * @param pageable Especifica paginação e ordenação.
     * @return ResponseEntity contendo uma página de artigos.
     */
    @GetMapping("/find-all")
    public ResponseEntity<ApiResponseDTO<Page<PaperDTO>>> findAllPaper(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Paper retrieved successfully.",
                service.findAll(pageable),
                null
        ));
    }
}
