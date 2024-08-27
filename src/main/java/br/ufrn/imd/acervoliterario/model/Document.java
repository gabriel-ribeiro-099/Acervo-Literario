package br.ufrn.imd.acervoliterario.model;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Superclasse mapeada que representa um documento no sistema.
 * Contém atributos comuns a todos os tipos de documentos.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@MappedSuperclass
public class Document extends BaseEntity {

    @NotNull
    @Column(nullable = false, name = "nome")
    private String nome;

    @NotNull
    @Column(nullable = false)
    private String autor;

    @NotNull
    @Column(nullable = false)
    private String areaConhecimento;

    @NotBlank
    private String fileName;

    @NotBlank
    private String path;

    @NotNull
    private Long size;

    @NotBlank
    private String extensionType;


    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAreaConhecimento() {
        return areaConhecimento;
    }

    public void setAreaConhecimento(String areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getExtensionType() {
        return extensionType;
    }

    public void setExtensionType(String extensionType) {
        this.extensionType = extensionType;
    }



    // Sobrescrita de equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Document document = (Document) o;
        return Objects.equals(nome, document.nome) &&
                Objects.equals(autor, document.autor) &&
                Objects.equals(areaConhecimento, document.areaConhecimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nome, autor, areaConhecimento);
    }
}
