package br.ufrn.imd.acervoliterario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object (DTO) que representa um livro no sistema.
 *
 * DTO usado para transferir dados de livros entre as camadas da aplicação,
 * contendo informações como título, autor, área de conhecimento, e detalhes do arquivo.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 *
 * @param id               O identificador único do livro.
 * @param nome             O nome ou título do livro.
 * @param autor            O autor do livro.
 * @param areaConhecimento A área de conhecimento à qual o livro pertence.
 * @param fileName         O nome do arquivo associado ao livro.
 * @param path             O caminho onde o arquivo do livro está armazenado.
 * @param size             O tamanho do arquivo em bytes.
 * @param extensionType    O tipo de extensão do arquivo (por exemplo, PDF, EPUB).
 * @param anoPublicacao    O ano de publicação do livro.
 * @param edicao           A edição do livro.
 * @param editora          A editora responsável pela publicação do livro.
 * @param ISBN             O código ISBN (International Standard Book Number) do livro.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BookDTO(
        long id,
        String nome,
        String autor,
        String areaConhecimento,
        String fileName,
        String path,
        Long size,
        String extensionType,
        String anoPublicacao,
        String edicao,
        String editora,
        String ISBN
) implements EntityDTO {

    /**
     * Converte o BookDTO em sua representação de resposta.
     *
     * @return Um novo objeto BookDTO com os mesmos dados.
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
                this.anoPublicacao(),
                this.edicao(),
                this.editora(),
                this.ISBN()
        );
    }
}
