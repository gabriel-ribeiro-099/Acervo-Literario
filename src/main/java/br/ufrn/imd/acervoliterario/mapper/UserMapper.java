package br.ufrn.imd.acervoliterario.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import br.ufrn.imd.acervoliterario.dto.UserDTO;
import br.ufrn.imd.acervoliterario.model.User;

/**
 * Mapeia objetos da entidade User para UserDTO e vice-versa.
 *
 * Implementa a interface DtoMapper e fornece a lógica para converter dados entre a entidade e o DTO,
 * facilitando o processo de transferência de dados entre as camadas da aplicação.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
//@Component
//public class UserMapper implements DtoMapper<User, UserDTO> {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    /**
//     * Converte uma entidade User para um DTO UserDTO.
//     *
//     * @param entity Entidade a ser convertida.
//     * @return DTO que representa a entidade User.
//     */
//    @Override
//    public UserDTO toDto(User entity) {
//        return new UserDTO(
//                entity.getId(),
//                entity.getLogin(),
//                null,
//                entity.getEmail()
//        );
//    }
//
//    /**
//     * Converte um DTO UserDTO para uma entidade User.
//     *
//     * @param userDTO DTO a ser convertido.
//     * @return Entidade que representa o DTO UserDTO.
//     */
//    @Override
//    public User toEntity(UserDTO userDTO) {
//        User user = User.builder()
//                .id(userDTO.id())
//                .login(userDTO.login())
//                .email(userDTO.email())
//                .build();
//
//        if (userDTO.password() != null) {
//            user.setPassword(passwordEncoder.encode(userDTO.password()));
//        }
//
//        return user;
//    }
//}

@Component
public class UserMapper implements DtoMapper<User, UserDTO> {

    @Override
    public UserDTO toDto(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getLogin(),
                null,
                entity.getEmail()
        );
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.id())
                .login(userDTO.login())
                .password(userDTO.password())
                .email(userDTO.email())
                .build();
    }
}