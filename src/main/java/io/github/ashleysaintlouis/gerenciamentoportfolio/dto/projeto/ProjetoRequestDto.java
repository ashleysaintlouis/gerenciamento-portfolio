package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoRequestDto(
        String nome,
        LocalDate dataInicio,
        LocalDate dataPrevisto,
        BigDecimal orcamento,
        String descricao,
        Long idResponsavel,
        StatusProjeto status
) {

}
