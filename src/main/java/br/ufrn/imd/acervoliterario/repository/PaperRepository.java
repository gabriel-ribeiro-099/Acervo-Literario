package br.ufrn.imd.acervoliterario.repository;

import br.ufrn.imd.acervoliterario.model.Paper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface para operações de repositório relacionadas aos Artigos.
 * Extende GenericRepository para herdar operações CRUD básicas.
 *
 * @author Nicole Nogueira
 * @version 1.0
 */
public interface PaperRepository extends GenericRepository<Paper> {

    /**
     * Encontra uma lista de Papers pelo nome.
     *
     * @param nome Nome dos Papers a serem encontrados.
     * @return Lista de Papers com o nome especificado.
     */
    List<Paper> findByNome(String nome);
}
