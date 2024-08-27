package br.ufrn.imd.acervoliterario.model.builder;

import br.ufrn.imd.acervoliterario.model.User;

/**
 * Builder para a classe User. Utilizado para criar instâncias de User
 * de forma mais flexível e controlada.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
public class UserBuilder {

    private long id;
    private String login;
    private String password;
    private String email;

    /**
     * Define o ID do usuário.
     *
     * @param id ID do usuário.
     * @return Instância atual do builder.
     */
    public UserBuilder id(long id){
        this.id = id;
        return this;
    }

    /**
     * Define o login do usuário.
     *
     * @param login Login do usuário.
     * @return Instância atual do builder.
     */
    public UserBuilder login(String login){
        this.login = login;
        return this;
    }

    /**
     * Define a senha do usuário.
     *
     * @param password Senha do usuário.
     * @return Instância atual do builder.
     */
    public UserBuilder password(String password){
        this.password = password;
        return this;
    }

    /**
     * Define o e-mail do usuário.
     *
     * @param email E-mail do usuário.
     * @return Instância atual do builder.
     */
    public UserBuilder email(String email){
        this.email = email;
        return this;
    }

    /**
     * Constrói uma nova instância de User com os valores fornecidos ao builder.
     *
     * @return uma nova instância de User.
     */
    public User build(){
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
