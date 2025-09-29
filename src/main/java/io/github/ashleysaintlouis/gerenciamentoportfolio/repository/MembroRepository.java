package io.github.ashleysaintlouis.gerenciamentoportfolio.repository;

import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembroRepository extends JpaRepository<Membro,Long> {

}
