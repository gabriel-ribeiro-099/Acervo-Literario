package br.ufrn.imd.acervoliterario.mapper;

import org.springframework.stereotype.Component;
import br.ufrn.imd.acervoliterario.dto.BookDTO;
import br.ufrn.imd.acervoliterario.model.Book;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapeia entidades do tipo {@link Book} para DTOs do tipo {@link BookDTO} e vice-versa.
 * <p>
 * Implementa a interface {@link DtoMapper} para fornecer as funcionalidades de conversão entre
 * entidades e seus respectivos DTOs.
 * </p>
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@Component
public class BookMapper implements DtoMapper<Book, BookDTO> {

    /**
     * Converte uma entidade {@link Book} para um {@link BookDTO}.
     *
     * @param entity A entidade a ser convertida.
     * @return Um {@link BookDTO} contendo os dados da entidade.
     */
    @Override
    public BookDTO toDto(Book entity) {
        return new BookDTO(
                entity.getId(),
                entity.getNome(),
                entity.getAutor(),
                entity.getAreaConhecimento(),
                entity.getFileName(),
                entity.getPath(),
                entity.getSize(),
                entity.getExtensionType(),
                entity.getAnoPublicacao(),
                entity.getEdicao(),
                entity.getEditora(),
                entity.getISBN()
        );
    }

    /**
     * Converte um {@link BookDTO} para uma entidade {@link Book}.
     *
     * @param bookDTO O DTO a ser convertido.
     * @return Uma entidade {@link Book} contendo os dados do DTO.
     */
    @Override
    public Book toEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.id());
        book.setNome(bookDTO.nome());
        book.setAutor(bookDTO.autor());
        book.setAreaConhecimento(bookDTO.areaConhecimento());
        book.setFileName(bookDTO.fileName());
        book.setPath(bookDTO.path());
        book.setSize(bookDTO.size());
        book.setExtensionType(bookDTO.extensionType());
        book.setAnoPublicacao(bookDTO.anoPublicacao());
        book.setEdicao(bookDTO.edicao());
        book.setEditora(bookDTO.editora());
        book.setISBN(bookDTO.ISBN());

        return book;
    }

    public List<BookDTO> toDtoList(List<Book> books) {
        return books.stream().map(this::toDto).collect(Collectors.toList());
    }
}
