package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.MembroMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembroService {
    @Autowired
    private MembroRepository membroRepository;
    @Autowired
    private MembroMapper membroMapper;

    public String cadastrarMembro(MembroDto membroDto){
        System.out.println(membroDto.toString());
        Membro membro = membroMapper.toEntityMembro(membroDto);
        System.out.println(membro.toString());
        Membro salvo = membroRepository.save(membro);
        System.out.println("Salvo: " + salvo.toString());
        return "Membro criado com sucesso";
    }
}
