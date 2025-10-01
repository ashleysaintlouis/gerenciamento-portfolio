package io.github.ashleysaintlouis.gerenciamentoportfolio.repository;

import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembroRepository extends JpaRepository<Membro,Long> {
    Optional<Membro> findByNome(String nome);
    Optional<Membro> findByIdExterno(Long idExterno);


}
