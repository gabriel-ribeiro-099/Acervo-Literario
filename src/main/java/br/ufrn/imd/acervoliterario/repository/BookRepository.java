package br.ufrn.imd.acervoliterario.repository;

import br.ufrn.imd.acervoliterario.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações de persistência da entidade `Book`.
 * Extende a interface `GenericRepository` para operações CRUD básicas.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@Repository
public interface BookRepository extends GenericRepository<Book> {

    /**
     * Encontra uma lista de livros pelo nome.
     *
     * @param nome Nome do livro a ser buscado.
     * @return Lista de livros com o nome correspondente.
     */
    List<Book> findByNome(String nome);

    /**
     * Encontra um livro pelo ISBN.
     *
     * @param ISBN ISBN do livro a ser buscado.
     * @return Optional contendo o livro encontrado, ou vazio se nenhum livro for encontrado.
     */
    Optional<Book> findByISBN(String ISBN);
}
