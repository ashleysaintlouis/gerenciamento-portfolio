package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.TipoClassificacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoResponseDto(String nome,
                                 LocalDate dataInicio,
                                 LocalDate dataPrevisto,
                                 BigDecimal orcamento,
                                 String descricao,
                                 Long responsavel,
                                 StatusProjeto status,
                                 TipoClassificacao classificacao) {
}
