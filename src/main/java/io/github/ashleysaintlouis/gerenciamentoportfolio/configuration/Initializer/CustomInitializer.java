//package io.github.ashleysaintlouis.gerenciamentoportfolio.configuration.Initializer;
//
//import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoRequestDto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.NotFoundException;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.MembroMapper;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.MembroRepository;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.service.MembroService;
//import io.github.ashleysaintlouis.gerenciamentoportfolio.service.ProjetoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Component
//public class CustomInitializer implements ApplicationRunner {
//    private final MembroService membroService;
//    private final ProjetoService projetoService;
//    private final MembroRepository membroRepository;
//    private final MembroMapper membroMapper;
//
//    @Autowired
//    public CustomInitializer(MembroService membroService, ProjetoService projetoService, MembroRepository membroRepository, MembroMapper membroMapper) {
//        this.membroService = membroService;
//        this.projetoService = projetoService;
//        this.membroRepository = membroRepository;
//        this.membroMapper = membroMapper;
//    }
//
//    @Override
//    public void run(ApplicationArguments args) {
//        // Criar e salvar membro
//
//        MembroRequestDto membroRequestDto_1 = new MembroRequestDto("Ashley", "Funcionario");
//        MembroRequestDto membroRequestDto_2 = new MembroRequestDto("Jean", "Funcionario");
//        MembroRequestDto membroRequestDto_3 = new MembroRequestDto("EXV", "Funcionario");
//        MembroRequestDto membroRequestDto_4 = new MembroRequestDto("Ashley", "Funcionario");
//        MembroRequestDto membroRequestDto_5 = new MembroRequestDto("Jean", "Funcionario");
//        MembroRequestDto membroRequestDto_6 = new MembroRequestDto("EXV", "Funcionario");
//        MembroRequestDto membroRequestDto_7 = new MembroRequestDto("Ashley", "Funcionario");
//        MembroRequestDto membroRequestDto_8 = new MembroRequestDto("Jean", "Funcionario");
//        MembroRequestDto membroRequestDto_9 = new MembroRequestDto("EXV", "Gerente");
//        MembroRequestDto membroRequestDto_10 = new MembroRequestDto("Ashley", "Gerente");
//        MembroRequestDto membroRequestDto_11 = new MembroRequestDto("Jean", "Gerente");
//        MembroRequestDto membroRequestDto_12 = new MembroRequestDto("EXV", "Gerente");
//
////        List <MembroRequestDto> listaMembroDto_1 = List.of(membroRequestDto_1, membroRequestDto_2, membroRequestDto_3, membroRequestDto_4, membroRequestDto_5, membroRequestDto_6, membroRequestDto_7, membroRequestDto_8, membroRequestDto_9, membroRequestDto_10, membroRequestDto_11);
////
////        List<Membro> membroListeEntity = membroMapper.toMembroEntity(listaMembroDto_1);
////        ListaMembroDto listaMembroDto = membroMapper.toListaMembroDto(membroListeEntity);
////        ListaMembroDto membroSalvo = membroService.salvarListaMembro(listaMembroDto);
//
//        MembroResponseDto membroSalvo = membroService.criarMembro(membroRequestDto_2);
//        Membro membroEntity = membroMapper.toMembroResponseEntity(membroSalvo);
//        Membro idMembro = membroRepository.findById(membroEntity.getId())
//                .orElseThrow(() -> new NotFoundException("Membro não encontrado"));
//
//        MembroDto membrodto = membroMapper.toMembroDto(idMembro);
//
//
//        // Criar projeto usando o membro salvo
//        String nomeProjeto = "Portfolio";
//        LocalDate dataInicio = LocalDate.of(2025, 2, 11);
//        LocalDate dataPrevisto = LocalDate.of(2025, 2, 11);
//        BigDecimal orcamento = BigDecimal.valueOf(100.00);
//        String descricaoProjeto = "Portfolio para projeto empresa";
//        StatusProjeto statusProjeto = StatusProjeto.EM_ANALISE;
//
//        ProjetoRequestDto projetoRequestDto = new ProjetoRequestDto(
//                nomeProjeto,
//                dataInicio,
//                dataPrevisto,
//                orcamento,
//                descricaoProjeto,
//                membrodto,
//                statusProjeto
//        );
//        System.out.println("Projeto para o membro: " + projetoRequestDto.toString());
//
//        projetoService.salvarProjeto(projetoRequestDto);
//    }
//}
