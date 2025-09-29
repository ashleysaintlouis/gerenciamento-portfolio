package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.ProjetoMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjetoService {
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private ProjetoMapper projetoMapper;

    public ProjetoDto cadastroProjeto(ProjetoDto projetoDto) {
        Projeto novoProjeto = projetoMapper.toProjeto(projetoDto);
        projetoRepository.save(novoProjeto);
        return projetoMapper.toProjetoDto(novoProjeto);
    }

}
