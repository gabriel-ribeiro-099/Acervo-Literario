package br.ufrn.imd.acervoliterario.service;

import br.ufrn.imd.acervoliterario.dto.PaperDTO;
import br.ufrn.imd.acervoliterario.mapper.PaperMapper;
import br.ufrn.imd.acervoliterario.model.Paper;
import br.ufrn.imd.acervoliterario.repository.PaperRepository;
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
 * Serviço responsável pela gestão dos Papers, incluindo operações CRUD e validações.
 *
 * Este serviço lida com operações de criação, atualização, exclusão e recuperação de Papers,
 * além de validações antes da persistência dos dados.
 *
 * @author Nicole Nogueira
 * @version 1.0
 */
@Service
public class PaperService implements GenericService<Paper, PaperDTO> {

    private final PaperRepository paperRepository;
    private final PaperMapper paperMapper;
    private final JwtService jwtService;

    /**
     * Construtor para a injeção de dependências.
     *
     * @param paperRepository Repositório de Papers.
     * @param paperMapper Mapper para conversão entre Paper e PaperDTO.
     * @param jwtService Serviço JWT usado para autenticação e extração de IDs de usuário.
     */
    @Autowired
    public PaperService(PaperRepository paperRepository, PaperMapper paperMapper, JwtService jwtService) {
        this.paperRepository = paperRepository;
        this.paperMapper = paperMapper;
        this.jwtService = jwtService;
    }

    /**
     * Retorna o repositório de Papers.
     *
     * @return Repositório de Papers.
     */
    @Override
    public GenericRepository<Paper> getRepository() {
        return paperRepository;
    }

    /**
     * Retorna o mapeador de Papers.
     *
     * @return Mapeador de Papers.
     */
    @Override
    public DtoMapper<Paper, PaperDTO> getDtoMapper() {
        return paperMapper;
    }

    /**
     * Cria um novo Paper com base no DTO fornecido.
     *
     * @param dto DTO contendo os dados do Paper.
     * @param request Objeto HttpServletRequest usado para obter o token JWT.
     * @return DTO do Paper criado.
     */
    public PaperDTO create(PaperDTO dto, HttpServletRequest request) {
        Paper entity = paperMapper.toEntity(dto);

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        entity.setUserId(userId);

        validateBeforeSave(entity);
        return paperMapper.toDto(paperRepository.save(entity));
    }

    /**
     * Atualiza um Paper existente com base no ID e nos dados fornecidos.
     *
     * @param id ID do Paper a ser atualizado.
     * @param updatedPaper Paper com os novos dados.
     * @param request Objeto HttpServletRequest usado para obter o token JWT.
     * @return Paper atualizado.
     * @throws ResourceNotFoundException Se o Paper com o ID fornecido não for encontrado.
     * @throws BusinessException Se o usuário não estiver autorizado a atualizar o Paper.
     */
    public Paper updateById(long id, Paper updatedPaper, HttpServletRequest request) {
        Paper existingPaper = paperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paper not found with id: " + id));

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        if (!existingPaper.getUserId().equals(userId)) {
            throw new BusinessException("Error: You are not authorized to update this paper.", HttpStatus.FORBIDDEN);
        }

        existingPaper.setNome(updatedPaper.getNome());
        existingPaper.setAutor(updatedPaper.getAutor());
        existingPaper.setAreaConhecimento(updatedPaper.getAreaConhecimento());
        existingPaper.setFileName(updatedPaper.getFileName());
        existingPaper.setPath(updatedPaper.getPath());
        existingPaper.setSize(updatedPaper.getSize());
        existingPaper.setExtensionType(updatedPaper.getExtensionType());
        existingPaper.setAno(updatedPaper.getAno());
        existingPaper.setLocal(updatedPaper.getLocal());

        return paperRepository.save(existingPaper);
    }

    /**
     * Exclui um Paper com base no ID fornecido.
     *
     * @param id ID do Paper a ser excluído.
     * @param request Objeto HttpServletRequest usado para obter o token JWT.
     * @throws BusinessException Se o Paper não for encontrado ou o usuário não estiver autorizado a excluí-lo.
     */
    public void deleteById(long id, HttpServletRequest request) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Error: Paper not found with id [" + id + "]", HttpStatus.NOT_FOUND));

        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtService.extractUserId(token);

        if (!paper.getUserId().equals(userId)) {
            throw new BusinessException("Error: You are not authorized to delete this paper.", HttpStatus.FORBIDDEN);
        }
        paperRepository.delete(paper);
    }

    /**
     * Recupera um Paper com base no ID fornecido.
     *
     * @param id ID do Paper a ser recuperado.
     * @return DTO do Paper recuperado.
     * @throws BusinessException Se o Paper com o ID fornecido não for encontrado.
     */
    public PaperDTO findById(long id) {
        Paper paper = paperRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Error: Paper not found with Id [" + id + "]", HttpStatus.NOT_FOUND));

        return paperMapper.toDto(paper);
    }

    /**
     * Encontra uma lista de Papers pelo nome.
     *
     * @param nome Nome dos Papers a serem encontrados.
     * @return Lista de DTOs dos Papers encontrados.
     * @throws BusinessException Se nenhum Paper for encontrado com o nome fornecido.
     */
    public List<PaperDTO> findByName(String nome) {
        List<Paper> papers = paperRepository.findByNome(nome);

        if (papers.isEmpty()) {
            throw new BusinessException("Error: No papers found with name [" + nome + "]", HttpStatus.NOT_FOUND);
        }

        return paperMapper.toDto(papers);
    }

    /**
     * Valida antes de salvar um Paper. Pode incluir validações específicas se necessário.
     *
     * @param entity Paper a ser validado.
     */
    public void validateBeforeSave(Paper entity) {
        validateId(entity.getId());
    }

    /**
     * Valida o ID do Paper para garantir que não haja duplicidade.
     *
     * @param id ID a ser validado.
     * @throws BusinessException Se o ID já estiver em uso por outro Paper.
     */
    private void validateId(Long id) {
        if (id != null) {
            Optional<Paper> paper = paperRepository.findById(id);
            if (paper.isPresent() && !paper.get().getId().equals(id)) {
                throw new BusinessException(
                        "Id inválido: " + id + ". Já existe um Paper cadastrado com esse Id.",
                        HttpStatus.BAD_REQUEST
                );
            }
        } else {
            throw new BusinessException("Error: O Id não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtém os nomes das propriedades que são nulas no objeto fornecido.
     *
     * @param source Objeto cujo valores de propriedades serão verificados.
     * @return Array contendo os nomes das propriedades nulas.
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
