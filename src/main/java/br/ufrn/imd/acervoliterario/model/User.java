package br.ufrn.imd.acervoliterario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.ufrn.imd.acervoliterario.model.builder.UserBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Entidade que representa um usuário no sistema.
 * Herda propriedades de `BaseEntity` e implementa a interface `UserDetails` para suporte à autenticação e autorização do Spring Security.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@Entity
@Table(name = "users")
@Where(clause = "active = true")
public class User extends BaseEntity implements UserDetails {


    @NotNull
    @Column(nullable = false, unique = true)
    private String login;

    @NotNull
    @Column(nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    public static UserBuilder builder(){
        return new UserBuilder();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        if(!super.equals(o))
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password)
                && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode(){
        return Objects.hash(super.hashCode(), id, login, password, email);
    }
}
