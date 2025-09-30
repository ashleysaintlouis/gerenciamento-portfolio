package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto;

import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;

public record ProjetoFiltroDto(String nome, StatusProjeto status, Long responsavelId) {}