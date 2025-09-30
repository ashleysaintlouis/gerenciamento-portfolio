package io.github.ashleysaintlouis.gerenciamentoportfolio.repository;

import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long>, JpaSpecificationExecutor<Projeto> {

    long countByMembrosIdAndStatusNotIn(Long membroId, List<StatusProjeto> statusExcluidos);

    @Query("SELECT COUNT(DISTINCT m.id) FROM Projeto p JOIN p.membros m")
    long countDistinctMembros();
}