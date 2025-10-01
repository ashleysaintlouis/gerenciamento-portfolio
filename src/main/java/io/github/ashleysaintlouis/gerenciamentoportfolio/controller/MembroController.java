package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;


import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.MembroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membros")
public class MembroController {
    @Autowired
    private MembroService membroService;


    @PostMapping
    @Operation(summary = "Criar membro",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membro encontrado"),
                    @ApiResponse(responseCode = "404", description = "Membro não encontrado")
            })
    public ResponseEntity<MembroResponseDto> criarMembro(@RequestBody MembroExternalDto dto) {
        MembroResponseDto novoMembro = membroService.criarMembro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMembro);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um membro pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membro encontrado"),
                    @ApiResponse(responseCode = "404", description = "Membro não encontrado")
            })
    public ResponseEntity<MembroResponseDto> buscarMembroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(membroService.buscarMembroPorId(id));
    }

    @GetMapping("/{nome}")
    @Operation(summary = "Busca um membro pelo nome",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membro encontrado"),
                    @ApiResponse(responseCode = "404", description = "Membro não encontrado")
            })
    public ResponseEntity<MembroResponseDto> buscarMembroPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(membroService.buscarMembroPorNome(nome));
    }

}
