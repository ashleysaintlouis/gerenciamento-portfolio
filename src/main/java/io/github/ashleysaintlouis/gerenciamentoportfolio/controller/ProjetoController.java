package io.github.ashleysaintlouis.gerenciamentoportfolio.controller;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;


@Controller
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    public ResponseEntity<ProjetoDto> cadastrarProjeto(ProjetoDto projetoDto) {
        ProjetoDto projeto = projetoService.cadastroProjeto(projetoDto);
        return new ResponseEntity<ProjetoDto>(projeto, HttpStatus.OK);
    }
}
