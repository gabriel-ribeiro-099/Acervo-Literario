package br.ufrn.imd.acervoliterario.mapper;

import br.ufrn.imd.acervoliterario.dto.FinalProjectDTO;
import br.ufrn.imd.acervoliterario.model.FinalProject;
import org.springframework.stereotype.Component;

/**
 * Implementa a interface DtoMapper e fornece a lógica para converter dados entre a entidade e o DTO,
 * facilitando o processo de transferência de dados entre as camadas da aplicação.
 *
 */
@Component
public class FinalProjectMapper implements DtoMapper<FinalProject, FinalProjectDTO> {

    /**
     * Converte uma entidade para um DTO.
     *
     * @author Beatriz Santana
     * @version 1.0
     *
     * @param entity Entidade a ser convertida.
     * @return DTO que representa a entidade.
     */
    @Override
    public FinalProjectDTO toDto(FinalProject entity) {
        return new FinalProjectDTO(
                entity.getId(),
                entity.getNome(),
                entity.getAutor(),
                entity.getAreaConhecimento(),
                entity.getFileName(),
                entity.getPath(),
                entity.getSize(),
                entity.getExtensionType(),
                entity.getAnoDefesa(),
                entity.getCurso(),
                entity.getInstituicao(),
                entity.getOrientador()
        );
    }

    /**
     * Converte um DTO para uma entidade.
     *
     * @param finalProjectDTO DTO a ser convertido.
     * @return Entidade que representa o DTO.
     */
    @Override
    public FinalProject toEntity(FinalProjectDTO finalProjectDTO) {
        FinalProject finalProject = new FinalProject();
        finalProject.setId(finalProjectDTO.id());
        finalProject.setNome(finalProjectDTO.nome());
        finalProject.setAutor(finalProjectDTO.autor());
        finalProject.setAreaConhecimento(finalProjectDTO.areaConhecimento());
        finalProject.setFileName(finalProjectDTO.fileName());
        finalProject.setPath(finalProjectDTO.path());
        finalProject.setSize(finalProjectDTO.size());
        finalProject.setExtensionType(finalProjectDTO.extensionType());
        finalProject.setAnoDefesa(finalProjectDTO.anoDefesa());
        finalProject.setCurso(finalProjectDTO.curso());
        finalProject.setInstituicao(finalProjectDTO.instituicao());
        finalProject.setOrientador(finalProjectDTO.orientador());

        return finalProject;
    }
}
