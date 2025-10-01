package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.RelatorioPortfolioDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping
    @Operation(summary = "Gera um relatório resumido do portfólio")
    public ResponseEntity<RelatorioPortfolioDto> gerarRelatorio() {
        return ResponseEntity.ok(relatorioService.gerarRelatorioPortfolio());
    }
}
