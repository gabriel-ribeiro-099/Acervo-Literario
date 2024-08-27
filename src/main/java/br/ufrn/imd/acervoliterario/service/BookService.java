package br.ufrn.imd.acervoliterario.service;

import br.ufrn.imd.acervoliterario.dto.BookDTO;
import br.ufrn.imd.acervoliterario.mapper.BookMapper;
import br.ufrn.imd.acervoliterario.model.Book;
import br.ufrn.imd.acervoliterario.repository.BookRepository;
import br.ufrn.imd.acervoliterario.utils.exception.BusinessException;
import br.ufrn.imd.acervoliterario.mapper.DtoMapper;
import br.ufrn.imd.acervoliterario.repository.GenericRepository;
import br.ufrn.imd.acervoliterario.utils.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Serviço para gerenciar operações relacionadas a livros.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@Service
public class BookService implements GenericService<Book, BookDTO> {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final JwtService jwtService;

    /**
     * Construtor da classe BookService.
     *
     * @param bookRepository o repositório de livros.
     * @param bookMapper o mapeador de entidades para DTOs de livros.
     * @param jwtService o serviço de manipulação de tokens JWT.
     */
    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper, JwtService jwtService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.jwtService = jwtService;
    }

    @Override
    public GenericRepository<Book> getRepository() {
        return bookRepository;
    }

    @Override
    public DtoMapper<Book, BookDTO> getDtoMapper() {
        return bookMapper;
    }

    /**
     * Cria um novo livro a partir de um DTO.
     *
     * @param dto Objeto DTO contendo as informações do livro.
     * @param request Requisição HTTP que contém o token de autorização.
     * @return DTO do livro criado.
     */
    public BookDTO create(BookDTO dto, HttpServletRequest request) {
        Book entity = bookMapper.toEntity(dto);

        String token = request.getHeader("Authorization").replace("Bearer ", "");

        Long userId = jwtService.extractUserId(token);

        entity.setUserId(userId);

        validateBeforeSave(entity);
        return bookMapper.toDto(bookRepository.save(entity));
    }

    @Override
    public BookDTO update(Long id, BookDTO dto) {
        Book updatedEntity = bookMapper.toEntity(dto);
        Book bdEntity = bookRepository.findById(id).orElseThrow(() -> new BusinessException(
                "Error: Book not found with id [" + id + "]", HttpStatus.NOT_FOUND
        ));

        BeanUtils.copyProperties(updatedEntity, bdEntity, getNullPropertyNames(updatedEntity));
        validateBeforeSave(bdEntity);
        return bookMapper.toDto(bookRepository.save(bdEntity));
    }

    /**
     * Atualiza um livro existente com base em seu ISBN.
     *
     * @param isbn ISBN do livro a ser atualizado.
     * @param updatedBook Objeto contendo as novas informações do livro.
     * @param request Requisição HTTP que contém o token de autorização.
     * @return Livro atualizado.
     */
    public Book updateByISBN(String isbn, Book updatedBook, HttpServletRequest request) {
        Book existingBook = bookRepository.findByISBN(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        if (!existingBook.getUserId().equals(userId)) {
            throw new BusinessException("Error: You are not authorized to update this book.", HttpStatus.FORBIDDEN);
        }

        existingBook.setNome(updatedBook.getNome());
        existingBook.setAutor(updatedBook.getAutor());
        existingBook.setAreaConhecimento(updatedBook.getAreaConhecimento());
        existingBook.setFileName(updatedBook.getFileName());
        existingBook.setPath(updatedBook.getPath());
        existingBook.setSize(updatedBook.getSize());
        existingBook.setExtensionType(updatedBook.getExtensionType());
        existingBook.setAnoPublicacao(updatedBook.getAnoPublicacao());
        existingBook.setEdicao(updatedBook.getEdicao());
        existingBook.setEditora(updatedBook.getEditora());

        return bookRepository.save(existingBook);
    }

    /**
     * Exclui um livro com base em seu ISBN.
     *
     * @param isbn ISBN do livro a ser excluído.
     * @param request Requisição HTTP que contém o token de autorização.
     */
    public void deleteByISBN(String isbn, HttpServletRequest request) {
        Book book = bookRepository.findByISBN(isbn)
                .orElseThrow(() -> new BusinessException("Error: Book not found with ISBN [" + isbn + "]", HttpStatus.NOT_FOUND));

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        if (!book.getUserId().equals(userId)) {
            throw new BusinessException("Error: You are not authorized to delete this book.", HttpStatus.FORBIDDEN);
        }
        bookRepository.delete(book);
    }

    /**
     * Encontra um livro com base em seu ISBN.
     *
     * @param isbn ISBN do livro.
     * @return DTO do livro encontrado.
     */
    public BookDTO findByISBN(String isbn) {
        Book book = bookRepository.findByISBN(isbn)
                .orElseThrow(() -> new BusinessException("Error: Book not found with ISBN [" + isbn + "]", HttpStatus.NOT_FOUND));

        return bookMapper.toDto(book);
    }

    /**
     * Encontra livros com base no nome.
     *
     * @param nome Nome do livro.
     * @return Lista de DTOs dos livros encontrados.
     */
    public List<BookDTO> findByName(String nome) {
        List<Book> books = bookRepository.findByNome(nome);

        if (books.isEmpty()) {
            throw new BusinessException("Error: No books found with name [" + nome + "]", HttpStatus.NOT_FOUND);
        }

        return bookMapper.toDto(books);
    }

    /**
     * Valida um livro antes de salvá-lo no banco de dados.
     *
     * @param entity Livro a ser validado.
     */
   /** public void validateBeforeSave(Book entity) {
        validateIsbn(entity.getISBN(), entity.getId());
    }*/

    /**
     * Valida o ISBN de um livro.
     *
     * @param isbn ISBN a ser validado.
     * @param id ID do livro, usado para verificar se não há duplicações.
     */
    private void validateIsbn(String isbn, Long id) {
        if (isbn != null) {
            Optional<Book> book = bookRepository.findByISBN(isbn);
            if (book.isPresent() && !book.get().getId().equals(id)) {
                throw new BusinessException(
                        "ISBN inválido: " + isbn + ". Já existe um livro cadastrado com esse ISBN.",
                        HttpStatus.BAD_REQUEST
                );
            }
        } else {
            throw new BusinessException("Error: O ISBN não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtém os nomes das propriedades nulas de um objeto.
     *
     * @param source Objeto do qual extrair as propriedades nulas.
     * @return Array de strings contendo os nomes das propriedades nulas.
     */
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        return emptyNames.toArray(new String[0]);
    }




}
