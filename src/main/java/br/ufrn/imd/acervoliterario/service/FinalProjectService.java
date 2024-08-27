package br.ufrn.imd.acervoliterario.service;

import br.ufrn.imd.acervoliterario.dto.FinalProjectDTO;
import br.ufrn.imd.acervoliterario.mapper.FinalProjectMapper;
import br.ufrn.imd.acervoliterario.model.FinalProject;
import br.ufrn.imd.acervoliterario.model.Paper;
import br.ufrn.imd.acervoliterario.repository.FinalProjectRepository;
import br.ufrn.imd.acervoliterario.utils.exception.BusinessException;
import br.ufrn.imd.acervoliterario.mapper.DtoMapper;
import br.ufrn.imd.acervoliterario.repository.GenericRepository;
import br.ufrn.imd.acervoliterario.utils.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Serviço para gerenciar operações relacionadas a projetos finais.
 *
 * @author Nicole Nogueira
 * @version 1.0
 */
@Service
public class FinalProjectService implements GenericService<FinalProject, FinalProjectDTO> {

    private final FinalProjectRepository finalProjectRepository;
    private final FinalProjectMapper finalProjectMapper;
    private final JwtService jwtService;

    /**
     * Construtor da classe FinalProjectService.
     *
     * @param finalProjectRepository o repositório de projetos finais.
     * @param finalProjectMapper o mapeador de entidades para DTOs de projetos finais.
     * @param jwtService o serviço de manipulação de tokens JWT.
     */
    @Autowired
    public FinalProjectService(FinalProjectRepository finalProjectRepository, FinalProjectMapper finalProjectMapper, JwtService jwtService) {
        this.finalProjectRepository = finalProjectRepository;
        this.finalProjectMapper = finalProjectMapper;
        this.jwtService = jwtService;
    }

    @Override
    public GenericRepository<FinalProject> getRepository() {
        return finalProjectRepository;
    }

    @Override
    public DtoMapper<FinalProject, FinalProjectDTO> getDtoMapper() {
        return finalProjectMapper;
    }

    /**
     * Cria um novo projeto final a partir de um DTO.
     *
     * @param dto Objeto DTO contendo as informações do projeto final.
     * @param request Requisição HTTP que contém o token de autorização.
     * @return DTO do projeto final criado.
     */
    public FinalProjectDTO create(FinalProjectDTO dto, HttpServletRequest request) {
        FinalProject entity = finalProjectMapper.toEntity(dto);

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        entity.setUserId(userId);

        validateBeforeSave(entity);
        return finalProjectMapper.toDto(finalProjectRepository.save(entity));
    }

    public FinalProject updateById(long id, FinalProject updatedFinalProject, HttpServletRequest request) {
        FinalProject existingFinalProject = finalProjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Final Project not found with id: " + id));

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        if (!existingFinalProject.getUserId().equals(userId)) {
            throw new BusinessException("Error: You are not authorized to update this final project.", HttpStatus.FORBIDDEN);
        }

        existingFinalProject.setNome(updatedFinalProject.getNome());
        existingFinalProject.setAutor(updatedFinalProject.getAutor());
        existingFinalProject.setAreaConhecimento(updatedFinalProject.getAreaConhecimento());
        existingFinalProject.setFileName(updatedFinalProject.getFileName());
        existingFinalProject.setPath(updatedFinalProject.getPath());
        existingFinalProject.setSize(updatedFinalProject.getSize());
        existingFinalProject.setExtensionType(updatedFinalProject.getExtensionType());
        existingFinalProject.setAnoDefesa(updatedFinalProject.getAnoDefesa());
        existingFinalProject.setCurso(updatedFinalProject.getCurso());
        existingFinalProject.setInstituicao(updatedFinalProject.getInstituicao());
        existingFinalProject.setOrientador(updatedFinalProject.getOrientador());



        return finalProjectRepository.save(existingFinalProject);
    }

    public void deleteById(long id, HttpServletRequest request) {
        FinalProject finalProject = finalProjectRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Error: Final Project not found with id [" + id + "]", HttpStatus.NOT_FOUND));

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        if (!finalProject.getUserId().equals(userId)) {
            throw new BusinessException("Error: You are not authorized to delete this Final Project.", HttpStatus.FORBIDDEN);
        }
        finalProjectRepository.delete(finalProject);
    }

    /**
     * Encontra projetos finais com base no nome.
     *
     * @param nome Nome do projeto final.
     * @return Lista de DTOs dos projetos finais encontrados.
     */
    public List<FinalProjectDTO> findByName(String nome) {
        List<FinalProject> finalProjects = finalProjectRepository.findByNome(nome);

        if (finalProjects.isEmpty()) {
            throw new BusinessException("Error: No final projects found with name [" + nome + "]", HttpStatus.NOT_FOUND);
        }

        return finalProjectMapper.toDto(finalProjects);
    }

    /**
     * Valida um projeto final antes de salvá-lo no banco de dados.
     *
     * @param entity Projeto final a ser validado.
     */
    public void validateBeforeSave(FinalProject entity) {
        validateId(entity.getId());
    }
    private void validateId(Long id) {
        if (id != null) {
            Optional<FinalProject> finalProject = finalProjectRepository.findById(id);
            if (finalProject.isPresent() && !finalProject.get().getId().equals(id)) {
                throw new BusinessException(
                        "Id inválido: " + id + ". Já existe um TCC cadastrado com esse Id.",
                        HttpStatus.BAD_REQUEST
                );
            }
        } else {
            throw new BusinessException("Error: O Id não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Obtém os nomes das propriedades nulas de um objeto.
     *
     * @param source Objeto do qual extrair as propriedades nulas.
     * @return Array de strings contendo os nomes das propriedades nulas.
     */
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        return emptyNames.toArray(new String[0]);
    }
}
