package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.BusinessException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.client.ExternalApiService;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.NotFoundException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.MembroMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.MembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;
    @Autowired
    private MembroMapper membroMapper;
    @Autowired
    private ExternalApiService externalApiService;

    public MembroResponseDto criarMembroSemIdExterno(MembroExternalDto dto) {
        if (dto == null) {
            throw new NotFoundException("Membro não pode ser nulo: " + dto.toString());
        }
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
        System.out.println("Persistença membro "  + membro + " no banco\n");
        return membroMapper.toMembroResponseDto(membro);
    }

    public MembroResponseDto criarMembro(MembroExternalDto dto) {
        if (dto == null) {
            throw new NotFoundException("Membro não pode ser nulo: " + dto.toString());
        }
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
        System.out.println("Persistença membro "  + membro + " no banco\n");
        return membroMapper.toMembroResponseDto(membro);
    }



    public Membro buscarMembroEntity(Long id) {
        Membro membro = membroRepository.findByIdExterno(id)
                .orElseThrow(() -> new NotFoundException("Membro não encontrado: " + id));
        System.out.println("Buscando membro \n" + membro);
        return membro;
    }

    public MembroResponseDto buscarMembroPorId(Long id) {
        Membro membro = membroRepository.findByIdExterno(id)
                .orElseThrow(() -> new NotFoundException("Membro não encontrado: " + id));
        System.out.println("Buscando membro \n" + membro);
        return membroMapper.toMembroResponseDto(membro);
    }

    public MembroExternalDto buscarMembroExternalSeNaoExisteCriaNovo(MembroExternalDto dto) {
        Optional<Membro> membro = membroRepository.findByIdExterno(dto.id());
        if (membro.isPresent()) {
            return membroMapper.toMembroExternalDto(membro.get());
        }
        Membro novoMembro = new Membro();
        novoMembro.setNome(dto.nome());
        novoMembro.setCargo(dto.cargo());
        novoMembro.setIdExterno(dto.id());
        return membroMapper.toMembroExternalDto(novoMembro);
    }

    public MembroResponseDto buscarMembroPorNome(String nome) {
        Membro membro = membroRepository.findByNome(nome)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado: " + nome));
        System.out.println("Buscando membro por Nome\n" + membro);
        return membroMapper.toMembroResponseDto(membro);
    }

    public List<MembroResponseDto> listarMembros() {
        List<Membro> membros = membroRepository.findAll();
        return membroMapper.toMembro(membros);
    }

    public boolean eFuncionario(String cargo) {
        System.out.println("Verificar cargo\n");
        System.out.println("Membro " + cargo + " do membro: " );
        return "funcionario".equalsIgnoreCase(cargo);
    }

}