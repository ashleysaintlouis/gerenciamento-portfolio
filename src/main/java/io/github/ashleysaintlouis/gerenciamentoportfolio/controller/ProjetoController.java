package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.*;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @PostMapping
    @Operation(summary = "Cria um novo projeto")
    public ResponseEntity<ProjetoResponseDto> criarProjeto(@RequestBody @Valid ProjetoDto dto) {
        ProjetoResponseDto novoProjeto = projetoService.salvarProjeto(dto);
        return new ResponseEntity<>(novoProjeto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um projeto pelo ID")
    public ResponseEntity<ProjetoResponseDto> buscarProjetoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarProjetoDtoPorId(id));
    }

    @GetMapping
    @Operation(summary = "Lista todos os projetos com filtros e paginação")
    public ResponseEntity<Page<ProjetoResponseDto>> listarProjetos(ProjetoFiltroDto filtro, Pageable pageable) {
        return ResponseEntity.ok(projetoService.listarTodos(filtro, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um projeto existente")
    public ResponseEntity<ProjetoResponseDto> atualizarProjeto(@PathVariable Long id, @RequestBody @Valid ProjetoDto dto) {
        return ResponseEntity.ok(projetoService.atualizarProjeto(id, dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de um projeto")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestBody @Valid AtualizarStatusDto dto) {
        projetoService.atualizarStatusProjeto(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projetoId}/membros/{membroId}")
    @Operation(summary = "Adiciona um membro a um projeto")
    public ResponseEntity<Void> adicionarMembro(@PathVariable Long projetoId, @PathVariable Long membroId) {
        projetoService.adicionarMembroAoProjeto(membroId, projetoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um projeto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirProjeto(@PathVariable Long id) {
        projetoService.excluirProjeto(id);
    }

    @GetMapping("/relatorio")
    @Operation(summary = "Gera um relatório resumido do portfólio")
    public ResponseEntity<RelatorioPortfolioDto> gerarRelatorio() {
        return ResponseEntity.ok(projetoService.gerarRelatorioPortfolio());
    }
}