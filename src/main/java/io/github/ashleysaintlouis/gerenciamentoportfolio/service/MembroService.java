package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.service.client.MembroApiClient;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
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
//    @Autowired
//    private final String apiExterna = "http://localhost:8083/criar-membro";

    public MembroResponseDto criarMembro(MembroRequestDto dto) {
        Membro membroExterno = membroApiClient.criarMembro(dto);
        System.out.println("Projeto para o membro: " + membroExterno.toString());
        Membro membroCriado = membroRepository.save(membroExterno);
        System.out.println("Membro criado: " + membroCriado.toString());
        return membroMapper.toMembroResponseDto(membroCriado);
    }

    public Membro buscarMembroEntity(Long id) {
        Membro membro = membroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membro não encontrado: " + id));
        return membro;
    }

    public MembroResponseDto buscarMembroPorId(Long id) {
        Membro membro = membroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membro não encontrado: " + id));
        return membroMapper.toMembroResponseDto(membro);
    }

    public MembroResponseDto buscarMembroPorNome(String nome) {
        Membro membro = membroRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado: " + nome));
        return membroMapper.toMembroResponseDto(membro);
    }

    public Membro eFuncionario(Long id) {
        Membro membro = buscarMembroEntity(id);
        if (!"funcionario".equalsIgnoreCase(membro.getCargo())) {
            throw new BusinessException("Apenas membros com cargo 'Funcionario' podem ser associados a projetos.");
        }
        System.out.println("Funcionario com o membro: " + membro.getCargo());
        return membro;
    }

}