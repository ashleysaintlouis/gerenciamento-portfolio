package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.TipoClassificacao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoResponseDto(Long id,
                                 String nome,
                                 LocalDate dataInicio,
                                 LocalDate dataPrevisto,
                                 BigDecimal orcamento,
                                 String descricao,
                                 MembroDto idResponsavel,
                                 @Enumerated(EnumType.STRING)
                                 @NotNull(message = "Status não pode ser nulo")
                                 StatusProjeto status,
                                 @Enumerated(EnumType.STRING)
                                 @NotNull(message = "TipoClassificação não pode ser nulo")
                                 TipoClassificacao classificacao) {
}
