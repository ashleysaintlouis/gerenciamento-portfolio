package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.RelatorioPortfolioDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RelatorioService {
    @Autowired
    private ProjetoRepository projetoRepository;

    public RelatorioPortfolioDto gerarRelatorioPortfolio() {
        List<Projeto> projetos = projetoRepository.findAll();

        Map<StatusProjeto, Long> qtdProjetosPorStatus = projetos.stream()
                .collect(Collectors.groupingBy(Projeto::getStatus, Collectors.counting()));

        Map<StatusProjeto, BigDecimal> totalOrcadoPorStatus = projetos.stream()
                .collect(Collectors.groupingBy(Projeto::getStatus,
                        Collectors.reducing(BigDecimal.ZERO, Projeto::getOrcamento, BigDecimal::add)));

        double mediaDuracaoEncerrados = projetos.stream()
                .filter(p -> p.getStatus() == StatusProjeto.ENCERRADO && p.getDataFim() != null)
                .mapToLong(p -> ChronoUnit.DAYS.between(p.getDataInicio(), p.getDataFim()))
                .average()
                .orElse(0.0);

        long totalMembrosUnicos = projetoRepository.countDistinctMembros();

        return new RelatorioPortfolioDto(qtdProjetosPorStatus, totalOrcadoPorStatus, mediaDuracaoEncerrados, totalMembrosUnicos);
    }

}
