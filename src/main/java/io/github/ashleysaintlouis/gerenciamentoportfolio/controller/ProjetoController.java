package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public ResponseEntity<ProjetoResponseDto> criarProjeto(@RequestBody @Valid ProjetoRequestDto dto)
            throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(projetoService.salvarProjeto(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um projeto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
            })
    public ResponseEntity<ProjetoResponseDto> buscarProjetoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarProjetoDtoPorId(id));
    }

    @GetMapping
    @Operation(summary = "Lista todos os projetos com filtros e paginação")
    public ResponseEntity<Page<ProjetoResponseDto>> listarProjetos(
            @ParameterObject ProjetoFiltroDto filtro,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(projetoService.listarTodos(filtro, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um projeto existente")
    public ResponseEntity<ProjetoResponseDto> atualizarProjeto(
            @PathVariable Long id,
            @RequestBody @Valid ProjetoRequestDto dto) {
        return ResponseEntity.ok(projetoService.atualizarProjeto(id, dto));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualiza o status de um projeto")
    public ResponseEntity<AtualizarStatusDto> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusDto dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(projetoService.atualizarStatusProjeto(id, dto));
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


}
