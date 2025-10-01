package io.github.ashleysaintlouis.gerenciamentoportfolio.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.NotFoundException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.MembroMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.MembroRepository;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.MembroService;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class CustomInitializer implements ApplicationRunner {

    private final MembroService membroService;
    private final ProjetoService projetoService;
    private final MembroMapper membroMapper;
    private final MembroRepository membroRepository;

    @Autowired
    public CustomInitializer(MembroService membroService,
                             ProjetoService projetoService,
                             MembroRepository membroRepository,
                             MembroMapper membroMapper) {
        this.membroService = membroService;
        this.projetoService = projetoService;
        this.membroRepository = membroRepository;
        this.membroMapper = membroMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws JsonProcessingException {
        List<MembroExternalDto> membrosParaCriar = List.of(
                new MembroExternalDto(100L, "Ashley", "Funcionario"),
                new MembroExternalDto(101L, "Jean", "Funcionario"),
                new MembroExternalDto(102L, "EXV", "Funcionario"),
                new MembroExternalDto(103L, "Ashley", "Gerente"),
                new MembroExternalDto(104L, "Jean", "Gerente"),
                new MembroExternalDto(105L, "EXV", "Gerente")
        );

        membrosParaCriar.forEach(dto -> {
            MembroResponseDto salvo = membroService.criarMembro(dto);
            System.out.println("Membro criado: " + salvo.nome() + " | Cargo: " + salvo.cargo());
        });

        List<Membro> membrosSalvos = membroRepository.findAll();
        membrosSalvos.forEach(m -> System.out.println("Verificado no DB: " + m.getId() + " - " + m.getNome() + " | " + m.getCargo()));

        Membro membroReferencia = membrosSalvos.stream()
                .filter(m -> "Funcionario".equalsIgnoreCase(m.getCargo()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Nenhum membro com cargo 'Funcionario' encontrado"));

        MembroDto membroDto = membroMapper.toMembroDto(membroReferencia);

        ProjetoRequestDto projetoRequestDto = new ProjetoRequestDto(
                "Portfolio",
                LocalDate.of(2025, 2, 11),
                LocalDate.of(2025, 2, 11),
                BigDecimal.valueOf(100.00),
                "Portfolio para projeto empresa",
                membroDto.id(),
                StatusProjeto.EM_ANALISE
        );

        Long projetoId = projetoService.salvarProjeto(projetoRequestDto).id();
        System.out.println("Projeto criado com sucesso: " + projetoRequestDto);

        membrosSalvos.stream()
                .filter(m -> "Funcionario".equalsIgnoreCase(m.getCargo()))
                .forEach(m -> projetoService.adicionarMembroAoProjeto(m.getId(), projetoId));

        System.out.println("Todos os membros com cargo 'Funcionario' foram adicionados ao projeto.");
    }
}