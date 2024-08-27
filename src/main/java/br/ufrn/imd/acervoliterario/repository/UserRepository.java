package br.ufrn.imd.acervoliterario.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ufrn.imd.acervoliterario.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para a entidade User que estende o GenericRepository.
 *
 * Define operações adicionais específicas para a entidade User.
 *
 * @author Nicole Nogueira
 * @version 1.0
 */
public interface UserRepository extends GenericRepository<User> {

    /**
     * Encontra um usuário pelo login.
     *
     * @param login Login do usuário a ser encontrado.
     * @return Optional que pode conter o usuário com o login especificado.
     */
    Optional<User> findByLogin(String login);

    /**
     * Encontra um usuário pelo e-mail.
     *
     * @param email E-mail do usuário a ser encontrado.
     * @return Optional que pode conter o usuário com o e-mail especificado.
     */
    Optional<User> findByEmail(String email);
}
