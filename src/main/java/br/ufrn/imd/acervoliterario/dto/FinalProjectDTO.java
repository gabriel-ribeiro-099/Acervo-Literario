package br.ufrn.imd.acervoliterario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object (DTO) para representar um TCC (FinalProject).
 * Este DTO é usado para transferir dados de um TCC entre camadas da aplicação.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FinalProjectDTO(
        long id,  // Identificador único do TCC.
        String nome,  // Nome do TCC.
        String autor,  // Autor do TCC.
        String areaConhecimento,  // Área de conhecimento relacionada ao TCC.
        String fileName,  // Nome do arquivo do TCC.
        String path,  // Caminho para o arquivo do TCC.
        Long size,  // Tamanho do arquivo do TCC.
        String extensionType,  // Tipo de extensão do arquivo do TCC.
        String anoDefesa,  // Ano de defesa do TCC.
        String curso,  // Curso relacionado ao TCC.
        String instituicao,  // Instituição onde o TCC foi defendido.
        String orientador  // Nome do orientador do TCC.
) implements EntityDTO {

    /**
     * Converte o FinalProjectDTO atual para um novo BookDTO.
     * Este método é uma implementação do método toResponse() da interface EntityDTO.
     *
     * @author Beatriz Santana
     * @version 1.0
     *
     * @return Um novo BookDTO com os mesmos valores do FinalProjectDTO atual.
     */
    @Override
    public BookDTO toResponse() {
        return new BookDTO(
                this.id(),
                this.nome(),
                this.autor(),
                this.areaConhecimento(),
                this.fileName(),
                this.path(),
                this.size(),
                this.extensionType(),
                this.anoDefesa(),
                this.curso(),
                this.instituicao(),
                this.orientador()
        );
    }
}
