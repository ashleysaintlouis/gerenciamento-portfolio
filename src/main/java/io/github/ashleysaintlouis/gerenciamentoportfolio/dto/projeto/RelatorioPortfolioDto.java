package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto;

import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import java.math.BigDecimal;
import java.util.Map;

public record RelatorioPortfolioDto(
        Map<StatusProjeto, Long> quantidadeProjetosPorStatus,
        Map<StatusProjeto, BigDecimal> totalOrcadoPorStatus,
        Double mediaDuracaoDiasProjetosEncerrados,
        Long totalMembrosUnicosAlocados
) {}