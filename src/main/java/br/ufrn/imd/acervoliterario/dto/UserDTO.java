package br.ufrn.imd.acervoliterario.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO usado para transferir dados relacionados ao usuário entre as camadas da aplicação.
 * O campo `password` é omitido na representação de resposta para melhorar a segurança.
 *
 * @author Nicole Nogueira
 * @version 1.0
 *
 * @param id       O identificador do usuário.
 * @param login    O login do usuário.
 * @param password A senha do usuário (omitida na resposta).
 * @param email    O endereço de e-mail do usuário.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTO(
        long id,
        String login,
        String password,
        String email
) implements EntityDTO {

    /**
     * Converte o UserDTO em sua representação de resposta, omitindo a senha.
     *
     * @return Objeto {@code UserDTO} com a senha omitida.
     */
    @Override
    public UserDTO toResponse() {
        return new UserDTO(
                this.id(),
                this.login(),
                null,
                this.email()
        );
    }
}
