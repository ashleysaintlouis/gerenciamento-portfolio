package io.github.ashleysaintlouis.gerenciamentoportfolio.configuration.Initializer;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.StatusDto;
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
    @Autowired
    public CustomInitializer(MembroService membroService, ProjetoService projetoService) {
        this.membroService = membroService;
        this.projetoService = projetoService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        String nomeMembro = "Ashley";
        String cargo = "FUNCIONARIO";

        MembroDto membroDto = new MembroDto(nomeMembro, cargo);
        membroService.cadastrarMembro(membroDto);

        String nomeProjeto = "Portfolio";
        LocalDate dataInicio =(LocalDate.of(2025,02,11));
        LocalDate dataPrevisto =(LocalDate.of(2025,02,11));
        BigDecimal orcamento = BigDecimal.valueOf(100.00);
        String descricaoProjeto = "Portfolio para projeto empresa";
        StatusDto statusProjeto = StatusDto.EM_ANALISE;


        ProjetoDto projetoDto = new ProjetoDto(
                nomeProjeto,
                dataInicio,
                dataPrevisto,
                orcamento,
                descricaoProjeto,
                membroDto,
                statusProjeto
                );
        projetoService.cadastroProjeto(projetoDto);


    }
}
