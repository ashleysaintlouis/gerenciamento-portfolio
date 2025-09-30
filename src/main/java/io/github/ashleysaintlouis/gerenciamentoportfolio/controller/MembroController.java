package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;


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
@RequestMapping("/membros/external")
public class MembroController {
    @Autowired
    private MembroService membroService;


    public ResponseEntity<?> criarMembro(MembroRequestDto dto) {
        MembroResponseDto novoMembro = membroService.criarMembro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMembro);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um membro pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membro encontrado"),
                    @ApiResponse(responseCode = "404", description = "Membro não encontrado")
            })
    public ResponseEntity <MembroResponseDto> buscarMembroPorId(@PathVariable Long id) {
        MembroResponseDto membro = membroService.buscarMembroPorId(id);
        return ResponseEntity.ok(membro);
    }

    @GetMapping("/{nome}")
    @Operation(summary = "Busca um membro pelo nome",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membro encontrado"),
                    @ApiResponse(responseCode = "404", description = "Membro não encontrado")
            })
    public ResponseEntity<MembroResponseDto> buscarMembroPorNome(@PathVariable String nome) {
        MembroResponseDto membro = membroService.buscarMembroPorNome(nome);
        return ResponseEntity.ok(membro);
    }

}
