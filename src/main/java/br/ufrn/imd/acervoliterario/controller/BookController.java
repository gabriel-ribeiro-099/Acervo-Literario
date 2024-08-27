package br.ufrn.imd.acervoliterario.controller;

import br.ufrn.imd.acervoliterario.dto.ApiResponseDTO;
import br.ufrn.imd.acervoliterario.dto.BookDTO;
import br.ufrn.imd.acervoliterario.dto.EntityDTO;
import br.ufrn.imd.acervoliterario.mapper.BookMapper;
import br.ufrn.imd.acervoliterario.model.Book;
import br.ufrn.imd.acervoliterario.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

/**
 * Controlador responsável pela gestão de livros na aplicação.
 * Fornece endpoints para operações CRUD relacionadas a livros,
 * incluindo registro, edição, exclusão e busca de informações de livros.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/books")
public class BookController extends GenericController<Book, BookDTO, BookService> {

    private final BookMapper bookMapper;

    /**
     * Construtor para BookController.
     *
     * @param bookService Serviço responsável pelas operações relacionadas a livros.
     * @param bookMapper  Mapeador para conversão entre Book e BookDTO.
     */
    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        super(bookService);
        this.bookMapper = bookMapper;
    }

    /**
     * Registra um novo livro.
     *
     * @param dto     DTO contendo as informações do livro a ser criado.
     * @param request Objeto HttpServletRequest que contém informações sobre a requisição HTTP.
     * @return ResponseEntity contendo o DTO do livro criado.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<EntityDTO>> createBook(
            @Valid @RequestBody BookDTO dto,
            HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO<>(
                        true,
                        "Success: Book created successfully.",
                        service.create(dto, request).toResponse(),
                        null
                )
        );
    }

    /**
     * Atualiza as informações de um livro existente.
     *
     * @param isbn ISBN do livro a ser atualizado.
     * @param dto  DTO contendo as informações atualizadas do livro.
     * @return ResponseEntity contendo o DTO do livro atualizado.
     */
    @PutMapping("/edit/{isbn}")
    public ResponseEntity<ApiResponseDTO<BookDTO>> updateBook(
            @PathVariable String isbn,
            @Valid @RequestBody BookDTO dto,
            HttpServletRequest request) {

        Book bookEntity = bookMapper.toEntity(dto);
        Book updatedBook = service.updateByISBN(isbn, bookEntity, request);
        BookDTO updatedDTO = bookMapper.toDto(updatedBook);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDTO<>(
                        true,
                        "Success: Book updated successfully.",
                        updatedDTO,
                        null
                )
        );
    }

    /**
     * Exclui um livro pelo seu ISBN.
     *
     * @param isbn ISBN do livro a ser excluído.
     * @return ResponseEntity com status 200 (OK).
     */
    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<ApiResponseDTO<BookDTO>> deleteBook(@PathVariable String isbn, HttpServletRequest request) {
        service.deleteByISBN(isbn, request);
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Book has been successfully removed.",
                null,
                null));
    }

    /**
     * Recupera as informações de um livro pelo seu ISBN.
     *
     * @param isbn ISBN do livro a ser recuperado.
     * @return ResponseEntity contendo o DTO do livro.
     */
    @GetMapping("/find-by-isbn/{isbn}")
    public ResponseEntity<ApiResponseDTO<BookDTO>> findBookByISBN(@PathVariable String isbn) {
        BookDTO bookDTO = service.findByISBN(isbn);
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Book retrieved successfully.",
                bookDTO,
                null
        ));
    }

    /**
     * Recupera uma lista de livros pelo nome.
     *
     * @param nome Nome dos livros a serem recuperados.
     * @return ResponseEntity contendo a lista de DTOs dos livros.
     */
    @GetMapping("/find-by-name/{nome}")
    public ResponseEntity<ApiResponseDTO<List<BookDTO>>> findBooksByName(
            @PathVariable String nome) {

        List<BookDTO> books = service.findByName(nome);

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Books retrieved successfully.",
                books,
                null
        ));
    }

    /**
     * Recupera todos os livros com paginação.
     *
     * @param pageable Especifica paginação e ordenação.
     * @return ResponseEntity contendo uma página de livros.
     */
    @GetMapping("/find-all")
    public ResponseEntity<ApiResponseDTO<Page<BookDTO>>> findAllBooks(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: Books retrieved successfully.",
                service.findAll(pageable),
                null
        ));
    }



}