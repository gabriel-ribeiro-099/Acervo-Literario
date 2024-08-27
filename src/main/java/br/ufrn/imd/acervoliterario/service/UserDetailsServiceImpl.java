package br.ufrn.imd.acervoliterario.service;

import br.ufrn.imd.acervoliterario.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.ufrn.imd.acervoliterario.repository.UserRepository;

import java.util.ArrayList;

/**
 * Implementação do serviço para carregar detalhes de usuários com base no nome de usuário.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Construtor para a injeção de dependências.
     *
     * @param userRepository Repositório de usuários.
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carrega os detalhes do usuário com base no nome de usuário fornecido.
     *
     * @param username Nome de usuário para buscar os detalhes.
     * @return Detalhes do usuário.
     * @throws UsernameNotFoundException Se o usuário não for encontrado com o nome fornecido.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
