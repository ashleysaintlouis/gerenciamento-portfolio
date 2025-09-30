package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto;

import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusDto(@NotNull StatusProjeto status) {}