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
        Projeto novoProjeto = projetoMapper.toProjetoEntity(projetoDto);
        calcularPrazo(novoProjeto);
        projetoRepository.save(novoProjeto);
        System.out.println(novoProjeto.toString());
        return projetoMapper.toProjetoDto(novoProjeto);
    }

    public int calcularPrazo(Projeto projeto) {
        int mes = projeto.getDataFim().compareTo(projeto.getDataInicio());
        System.out.println("Mes: " + mes);

        return mes;
    }

    public boolean riscoBaixo(ProjetoDto projetoDto) {
        Projeto projeto = projetoMapper.toProjetoEntity(projetoDto);
        return true;
    }
}
