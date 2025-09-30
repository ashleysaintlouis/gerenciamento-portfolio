package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;


import io.github.ashleysaintlouis.gerenciamentoportfolio.service.client.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-external")
public class MembroExternalController {
    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping
    public ResponseEntity<?> getAllMembro() {
        String allDatas = externalApiService.getAllDatas();
        return ResponseEntity.ok(allDatas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMembroById(@PathVariable String id) {
        String membersId = externalApiService.getMembroId(id);
        return ResponseEntity.ok(membersId);
    }


}
