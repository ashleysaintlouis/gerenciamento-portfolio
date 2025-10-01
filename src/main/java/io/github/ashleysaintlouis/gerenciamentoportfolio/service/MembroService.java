package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.client.ExternalApiService;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.BusinessException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.NotFoundException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.MembroMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;
    @Autowired
    private MembroMapper membroMapper;
    @Autowired
    private ExternalApiService externalApiService;

    public MembroResponseDto criarMembro(MembroExternalDto dto) {
        Optional<Membro> membroExistente = membroRepository.findByIdExterno(dto.id());
        Membro membro;
        if (membroExistente.isPresent()) {
            membro = membroExistente.get();
        } else {
            membro = new Membro();
            membro.setNome(dto.nome());
            membro.setCargo(dto.cargo());
            membro.setIdExterno(dto.id());
            membro = membroRepository.save(membro);
        }
        System.out.println("Projeto para o membro: " + membro);
        return membroMapper.toMembroResponseDto(membro);
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