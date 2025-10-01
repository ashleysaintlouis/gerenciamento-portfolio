package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.client.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-external")
public class MembroExternalController {

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping
    public ResponseEntity<List<MembroExternalDto>> getAllMembros() {
        return ResponseEntity.ok(externalApiService.getAllDatas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembroExternalDto> getMembroById(@PathVariable String id) {
        return ResponseEntity.ok(externalApiService.getMembroId(id));
    }
}
