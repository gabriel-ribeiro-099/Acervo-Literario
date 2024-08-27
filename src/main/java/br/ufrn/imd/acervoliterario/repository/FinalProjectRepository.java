package br.ufrn.imd.acervoliterario.repository;

import br.ufrn.imd.acervoliterario.model.FinalProject;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface para operações de repositório relacionadas aos TCCs.
 * Extende GenericRepository para herdar operações CRUD básicas.
 *
 * @author Nicole Nogueira
 * @version 1.0
 */
public interface FinalProjectRepository extends GenericRepository<FinalProject> {

    /**
     * Encontra uma lista de projetos finais pelo nome.
     *
     * @param nome Nome dos projetos finais a serem encontrados.
     * @return Lista de projetos finais com o nome especificado.
     */
    List<FinalProject> findByNome(String nome);

    /**
     * Encontra um TCC pelo curso associado.
     *
     * @param curso Nome do curso para buscar o projeto final.
     * @return Optional contendo o projeto final encontrado, ou vazio se não for encontrado.
     */
    Optional<FinalProject> findByCurso(String curso);
}
