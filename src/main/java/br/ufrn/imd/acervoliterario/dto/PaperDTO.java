package br.ufrn.imd.acervoliterario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object (DTO) para representar um Paper.
 * Este DTO é usado para transferir dados de um Paper entre camadas da aplicação.
 *
 * @author Beatriz Santana
 * @version 1.0
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaperDTO(
        long id,  // Identificador único do Artigo.
        String nome,  // Nome do Artigo.
        String autor,  // Autor do Artigo.
        String areaConhecimento,  // Área de conhecimento relacionada ao Artigo.
        String fileName,  // Nome do arquivo do Artigo.
        String path,  // Caminho para o arquivo do Artigo.
        Long size,  // Tamanho do arquivo do Artigo.
        String extensionType,  // Tipo de extensão do arquivo do Artigo.
        String ano,  // Ano de publicação do Artigo.
        String local  // Local onde o Artigo foi publicado.
) implements EntityDTO {

    /**
     * Converte o PaperDTO atual para um novo PaperDTO.
     * Este método é uma implementação do método toResponse() da interface EntityDTO.
     *
     * @return Novo PaperDTO com os mesmos valores do PaperDTO atual.
     */
    @Override
    public PaperDTO toResponse() {
        return new PaperDTO(
                this.id(),
                this.nome(),
                this.autor(),
                this.areaConhecimento(),
                this.fileName(),
                this.path(),
                this.size(),
                this.extensionType(),
                this.ano(),
                this.local()
        );
    }
}
