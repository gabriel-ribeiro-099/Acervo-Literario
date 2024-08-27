package br.ufrn.imd.acervoliterario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

import java.util.Objects;

/**
 * Entidade que representa um Artigo (Paper) no sistema.
 * Herda propriedades da classe `Document`.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@Entity
@Table(name = "paper")
@Where(clause = "active = true")
public class Paper extends Document {

    @NotNull
    @Column(nullable = false)
    private String local;

    @NotNull
    @Column(nullable = false)
    private String ano;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    // Getters e Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    // Sobrescrita de equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Paper paper = (Paper) o;
        return Objects.equals(local, paper.local) && Objects.equals(ano, paper.ano);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), local, ano);
    }
}
