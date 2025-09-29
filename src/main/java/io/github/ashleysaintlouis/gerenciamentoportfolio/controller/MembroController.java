package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.MembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/membro")
public class MembroController {
    @Autowired
    private MembroService membroService;

    @PostMapping("/cadastro")
    public ResponseEntity <?> criarMembro(@RequestBody MembroDto membroDto){
        System.out.println(membroDto.toString());
        String membro = membroService.cadastrarMembro(membroDto);
        return new ResponseEntity<>("Membro criado com sucesso", HttpStatus.OK);
//                new ResponseEntity<>(membro, HttpStatus.OK);
    }
}
