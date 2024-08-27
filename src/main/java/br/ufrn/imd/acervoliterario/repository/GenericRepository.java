package br.ufrn.imd.acervoliterario.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import br.ufrn.imd.acervoliterario.model.BaseEntity;

import java.util.Optional;

/**
 * Uma interface genérica de repositório que define operações comuns de CRUD para
 * entidades na aplicação.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 *
 * @param <T> O tipo da entidade que estende BaseEntity.
 *
 * @NoRepositoryBean significa que a interface não será instanciada como um
 * bean de repositório pelo Spring.
 */
@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    /**
     * Sobrescreve o método padrão de exclusão por id para realizar a exclusão lógica
     * em vez de exclusão física. Em vez de remover a entidade do banco de dados,
     * define a flag "active" como {false} e salva a entidade atualizada.
     *
     * @param id O id da entidade a ser excluída.
     */
    @Override
    @Transactional
    default void deleteById(Long id){
        Optional<T> entity = findById(id);
        if(entity.isPresent()){
            entity.get().setActive(false);
            save(entity.get());
        }
    }

    /**
     * Sobrescreve o método padrão de exclusão para realizar a exclusão lógica
     * em vez de exclusão física. Marca a entidade como inativa e salva a entidade
     * atualizada.
     *
     * @param obj Entidade a ser excluída.
     */
    @Override
    @Transactional
    default void delete(T obj){
        obj.setActive(false);
        save(obj);
    }

    /**
     * Sobrescreve o método padrão de exclusão para realizar a exclusão lógica
     * em vez de exclusão física. Marca cada entidade na coleção como inativa
     * e salva as alterações.
     *
     * @param objects Coleção de entidades a serem excluídas.
     */
    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> objects){
        objects.forEach(entity -> deleteById(entity.getId()));
    }
}

