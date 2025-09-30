package io.github.ashleysaintlouis.gerenciamentoportfolio.configuration.Initializer;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoDto;
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

@Component
public class CustomInitializer implements ApplicationRunner {
    private final MembroService membroService;
    private final ProjetoService projetoService;
    private final MembroRepository membroRepository;
    private final MembroMapper membroMapper;

    @Autowired
    public CustomInitializer(MembroService membroService, ProjetoService projetoService, MembroRepository membroRepository, MembroMapper membroMapper) {
        this.membroService = membroService;
        this.projetoService = projetoService;
        this.membroRepository = membroRepository;
        this.membroMapper = membroMapper;
    }

    @Override
    public void run(ApplicationArguments args) {
        // Criar e salvar membro
        String nomeMembro = "Ashley";
        String cargo = "FUNCIONARIO";

        MembroDto membroDto = new MembroDto(nomeMembro, cargo);
        MembroResponseDto membroSalvo = membroService.criarMembro(membroDto);
        Membro membroEntity = membroMapper.toMembroResponseEntity(membroSalvo);
        Membro idMembro = membroRepository.findById(membroEntity.getId())
                .orElseThrow(() -> new NotFoundException("Membro não encontrado"));

        // Criar projeto usando o membro salvo
        String nomeProjeto = "Portfolio";
        LocalDate dataInicio = LocalDate.of(2025, 2, 11);
        LocalDate dataPrevisto = LocalDate.of(2025, 2, 11);
        BigDecimal orcamento = BigDecimal.valueOf(100.00);
        String descricaoProjeto = "Portfolio para projeto empresa";
        StatusProjeto statusProjeto = StatusProjeto.EM_ANALISE;

        ProjetoDto projetoDto = new ProjetoDto(
                nomeProjeto,
                dataInicio,
                dataPrevisto,
                orcamento,
                descricaoProjeto,
                idMembro.getId(), // 🔥 agora está usando o membro persistido
                statusProjeto
        );
        System.out.println("Projeto para o membro: " + projetoDto.toString());

        projetoService.salvarProjeto(projetoDto);
    }
}
