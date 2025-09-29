package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoDto(
        String nome,
        LocalDate dataInicio,
        LocalDate dataPrevisto,
        BigDecimal orcamento,
        String descricao,
        MembroDto membro,
        StatusDto status
) {}
