package br.ufrn.imd.acervoliterario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;

import java.util.Objects;

/**
 * Entidade que representa um Projeto de Conclusão de Curso (TCC) no sistema.
 * Herda propriedades da classe `Document`.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@Entity
@Table(name = "finalProject")
@Where(clause = "active = true")
public class FinalProject extends Document {

    @NotNull
    @Column(nullable = false)
    private String anoDefesa;

    @NotNull
    @Column(nullable = false)
    private String curso;

    @NotNull
    @Column(nullable = false)
    private String instituicao;

    @NotNull
    @Column(nullable = false)
    private String orientador;

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

    public String getAnoDefesa() {
        return anoDefesa;
    }

    public void setAnoDefesa(String anoDefesa) {
        this.anoDefesa = anoDefesa;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getOrientador() {
        return orientador;
    }

    public void setOrientador(String orientador) {
        this.orientador = orientador;
    }

    // Sobrescrita de equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FinalProject that = (FinalProject) o;
        return Objects.equals(anoDefesa, that.anoDefesa) &&
                Objects.equals(curso, that.curso) &&
                Objects.equals(instituicao, that.instituicao) &&
                Objects.equals(orientador, that.orientador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), anoDefesa, curso, instituicao, orientador);
    }
}
