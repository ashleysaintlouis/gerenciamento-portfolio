package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.*;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
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
    public ResponseEntity<ProjetoResponseDto> criarProjeto(@RequestBody @Valid ProjetoRequestDto dto) {
        ProjetoResponseDto novoProjeto = projetoService.salvarProjeto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProjeto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um projeto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
            })
    public ResponseEntity<ProjetoResponseDto> buscarProjetoPorId(@PathVariable Long id) {
        ProjetoResponseDto dto = projetoService.buscarProjetoDtoPorId(id);
        System.out.println("Projeto encontrado: " + dto.id());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    @Operation(summary = "Lista todos os projetos com filtros e paginação")
    public ResponseEntity<Page<ProjetoResponseDto>> listarProjetos(
            @ParameterObject ProjetoFiltroDto filtro,
            @ParameterObject Pageable pageable) {
        Page<ProjetoResponseDto> projetos = projetoService.listarTodos(filtro, pageable);
        return ResponseEntity.ok(projetos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um projeto existente")
    public ResponseEntity<ProjetoResponseDto> atualizarProjeto(
            @PathVariable Long id,
            @RequestBody @Valid ProjetoRequestDto dto) {
        ProjetoResponseDto atualizado = projetoService.atualizarProjeto(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de um projeto")
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusDto dto) {
        projetoService.atualizarStatusProjeto(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projetoId}/membros/{membroId}")
    @Operation(summary = "Adiciona um membro a um projeto")
    public ResponseEntity<Void> adicionarMembro(
            @PathVariable Long projetoId,
            @PathVariable Long membroId) {
        projetoService.adicionarMembroAoProjeto(membroId, projetoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um projeto")
    public ResponseEntity<Void> excluirProjeto(@PathVariable Long id) {
        projetoService.excluirProjeto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio")
    @Operation(summary = "Gera um relatório resumido do portfólio")
    public ResponseEntity<RelatorioPortfolioDto> gerarRelatorio() {
        RelatorioPortfolioDto relatorio = projetoService.gerarRelatorioPortfolio();
        return ResponseEntity.ok(relatorio);
    }
}
