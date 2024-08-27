package br.ufrn.imd.acervoliterario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

import java.util.Objects;
/**
 * Entidade que representa um livro no sistema. Herda de Document e adiciona atributos específicos de livros.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@Entity
@Table(name = "book")
@Where(clause = "active = true")
public class Book extends Document{

    @NotNull
    @Column(nullable = false)
    private String anoPublicacao;

    @NotNull
    @Column(nullable = false)
    private String edicao;

    @NotNull
    @Column(nullable = false)
    private String editora;

    @NotNull
    @Column(nullable = false, unique = true)
    private String ISBN;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(String anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(anoPublicacao, book.anoPublicacao) && Objects.equals(edicao, book.edicao) && Objects.equals(editora, book.editora) && Objects.equals(ISBN, book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), anoPublicacao, edicao, editora, ISBN);
    }
}
