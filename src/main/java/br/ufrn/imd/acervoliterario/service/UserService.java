package br.ufrn.imd.acervoliterario.service;

import br.ufrn.imd.acervoliterario.dto.UserDTO;
import br.ufrn.imd.acervoliterario.mapper.UserMapper;
import br.ufrn.imd.acervoliterario.model.User;
import br.ufrn.imd.acervoliterario.repository.UserRepository;
import br.ufrn.imd.acervoliterario.repository.GenericRepository;
import br.ufrn.imd.acervoliterario.utils.exception.BusinessException;
import br.ufrn.imd.acervoliterario.mapper.DtoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Serviço para gerenciar operações relacionadas aos usuários.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@Service
public class UserService implements GenericService<User, UserDTO> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor para a injeção de dependências.
     *
     * @param userRepository O repositório de usuários.
     * @param userMapper O mapper para conversão entre User e UserDTO.
     * @param passwordEncoder O codificador de senhas.
     */
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public GenericRepository<User> getRepository() {
        return userRepository;
    }

    @Override
    public DtoMapper<User, UserDTO> getDtoMapper() {
        return userMapper;
    }

    /**
     * Cria um novo usuário a partir do DTO fornecido.
     *
     * @param dto DTO do usuário a ser criado.
     * @return DTO do usuário criado.
     */
    @Override
    public UserDTO create(UserDTO dto) {
        User entity = userMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        validateBeforeSave(entity);
        return userMapper.toDto(userRepository.save(entity));
    }

    /**
     * Atualiza um usuário existente com base no ID e no DTO fornecido.
     *
     * @param id ID do usuário a ser atualizado.
     * @param dto DTO com os dados atualizados do usuário.
     * @return DTO do usuário atualizado.
     */
    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User updatedEntity = userMapper.toEntity(dto);
        User bdEntity = userRepository.findById(id).orElseThrow(() -> new BusinessException(
                "Error: User not found with id [" + id + "]", HttpStatus.NOT_FOUND
        ));

        BeanUtils.copyProperties(updatedEntity, bdEntity, getNullPropertyNames(updatedEntity));
        validateBeforeSave(bdEntity);
        return userMapper.toDto(userRepository.save(bdEntity));
    }

    /**
     * Exclui um usuário com base no ID fornecido.
     *
     * @param id ID do usuário a ser excluído.
     * @throws BusinessException Se o usuário não for encontrado.
     */
    @Override
    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new BusinessException("Error: User not found with id [" + id + "]", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Recupera um usuário com base no ID fornecido.
     *
     * @param id ID do usuário a ser recuperado.
     * @return DTO do usuário.
     * @throws BusinessException Se o usuário não for encontrado.
     */
    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Error: User not found with id [" + id + "]", HttpStatus.NOT_FOUND));

        return userMapper.toDto(user);
    }

    /**
     * Recupera um usuário com base no login fornecido.
     *
     * @param login Login do usuário a ser recuperado.
     * @return DTO do usuário.
     * @throws BusinessException Se o usuário não for encontrado.
     */
    public UserDTO findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Error: User not found with login [" + login + "]", HttpStatus.NOT_FOUND));

        return userMapper.toDto(user);
    }

    /**
     * Valida o usuário antes de salvar no repositório.
     *
     * @param entity Usuário a ser validado.
     */
    public void validateBeforeSave(User entity) {
        validateLogin(entity.getLogin(), entity.getId());
        validateEmail(entity.getEmail(), entity.getId());
    }

    /**
     * Valida o login do usuário.
     *
     * @param login Login a ser validado.
     * @param id ID do usuário.
     * @throws BusinessException Se o login já estiver em uso por outro usuário.
     */
    private void validateLogin(String login, Long id) {
        if (login != null) {
            Optional<User> user = userRepository.findByLogin(login);
            if (user.isPresent() && !user.get().getId().equals(id)) {
                throw new BusinessException(
                        "Login inválido: " + login + ". Já existe um usuário cadastrado com esse login.",
                        HttpStatus.BAD_REQUEST
                );
            }
        } else {
            throw new BusinessException("Error: O login não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Valida o email do usuário.
     *
     * @param email E-mail a ser validado.
     * @param id ID do usuário.
     * @throws BusinessException Se o email já estiver em uso por outro usuário.
     */
    private void validateEmail(String email, long id) {
        if (email != null) {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent() && !user.get().getId().equals(id)) {
                throw new BusinessException(
                        "Email inválido: " + email + ". Já existe um usuário cadastrado com esse email.",
                        HttpStatus.BAD_REQUEST
                );
            }
        } else {
            throw new BusinessException("Error: O email não pode ser nulo.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtém os nomes das propriedades com valores nulos em um objeto.
     *
     * @param source Objeto fonte para verificar as propriedades.
     * @return Array com os nomes das propriedades que possuem valores nulos.
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
