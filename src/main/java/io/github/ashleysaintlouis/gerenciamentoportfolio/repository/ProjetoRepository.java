package io.github.ashleysaintlouis.gerenciamentoportfolio.repository;

import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
