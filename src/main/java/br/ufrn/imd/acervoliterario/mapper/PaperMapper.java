package br.ufrn.imd.acervoliterario.mapper;

import br.ufrn.imd.acervoliterario.dto.PaperDTO;
import br.ufrn.imd.acervoliterario.model.Paper;
import org.springframework.stereotype.Component;

/**
 * Mapeia objetos da entidade Paper para PaperDTO e vice-versa.
 *
 * Implementa a interface DtoMapper e fornece a lógica para converter dados entre a entidade e o DTO,
 * facilitando o processo de transferência de dados entre as camadas da aplicação.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@Component
public class PaperMapper implements DtoMapper<Paper, PaperDTO> {

    /**
     * Converte uma entidade Paper para um DTO PaperDTO.
     *
     * @param entity Entidade a ser convertida.
     * @return DTO que representa a entidade Paper.
     */
    @Override
    public PaperDTO toDto(Paper entity) {
        return new PaperDTO(
                entity.getId(),
                entity.getNome(),
                entity.getAutor(),
                entity.getAreaConhecimento(),
                entity.getFileName(),
                entity.getPath(),
                entity.getSize(),
                entity.getExtensionType(),
                entity.getAno(),
                entity.getLocal()
        );
    }

    /**
     * Converte um DTO PaperDTO para uma entidade Paper.
     *
     * @param paperDTO DTO a ser convertido.
     * @return Entidade que representa o DTO PaperDTO.
     */
    @Override
    public Paper toEntity(PaperDTO paperDTO) {
        Paper paper = new Paper();
        paper.setId(paperDTO.id());
        paper.setNome(paperDTO.nome());
        paper.setAutor(paperDTO.autor());
        paper.setAreaConhecimento(paperDTO.areaConhecimento());
        paper.setFileName(paperDTO.fileName());
        paper.setPath(paperDTO.path());
        paper.setSize(paperDTO.size());
        paper.setExtensionType(paperDTO.extensionType());
        paper.setAno(paperDTO.ano());
        paper.setLocal(paperDTO.local());

        return paper;
    }
}
