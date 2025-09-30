package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.client.MembroApiClient;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.BusinessException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.NotFoundException;
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
    private MembroApiClient membroApiClient;
    @Autowired
    private MembroMapper membroMapper;

    public MembroResponseDto criarMembro(MembroDto dto) {
        Membro membroExterno = membroApiClient.criarMembro(dto);
        System.out.println("Projeto para o membro: " + membroExterno.toString());
        Membro membroCriado = membroRepository.save(membroExterno);
        System.out.println("Membro criado: " + membroCriado.toString());
        return membroMapper.toMembroResponseDto(membroCriado);
    }

    public Membro buscarMembroEntity(Long id) {
        return membroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membro não encontrado: " + id));
    }

    public Membro eFuncionario(Long id) {
        Membro membro = buscarMembroEntity(id);
        if (!"Funcionario".equalsIgnoreCase(membro.getCargo())) {
            throw new BusinessException("Apenas membros com cargo 'Funcionario' podem ser associados a projetos.");
        }
        return membro;
    }
}