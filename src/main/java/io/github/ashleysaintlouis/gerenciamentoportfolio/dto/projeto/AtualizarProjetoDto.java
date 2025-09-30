package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto;

import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;

public record AtualizarProjetoDto(
        Long id,
        StatusProjeto status
) {
}
