package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.AtualizarStatusDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.BusinessException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;
    @Mock
    private MembroService membroService;
    @InjectMocks
    private ProjetoService projetoService;

    private Projeto projeto;
    private Membro membro;

    @BeforeEach
    void setUp() {
        projeto = new Projeto();
        projeto.setId(1L);
        projeto.setStatus(StatusProjeto.EM_ANALISE);
        projeto.setMembros(new ArrayList<>());

        membro = new Membro();
        membro.setId(1L);
        membro.setCargo("Funcionario");
    }

    @Test
    void testExcluirProjeto_Falha_StatusIniciado() {
        projeto.setStatus(StatusProjeto.INICIADO);
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        assertThrows(BusinessException.class, () -> projetoService.excluirProjeto(1L));
    }

    @Test
    void testAtualizarStatus_Sucesso_SequenciaCorreta() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        projeto.getMembros().add(membro); // Adiciona membro para permitir início

        projetoService.atualizarStatusProjeto(1L, new AtualizarStatusDto(StatusProjeto.ANALISE_REALIZADA));
        assertEquals(StatusProjeto.ANALISE_REALIZADA, projeto.getStatus());

        projetoService.atualizarStatusProjeto(1L, new AtualizarStatusDto(StatusProjeto.ANALISE_APROVADA));
        assertEquals(StatusProjeto.ANALISE_APROVADA, projeto.getStatus());
    }

    @Test
    void testAtualizarStatus_Falha_PularEtapa() {
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        assertThrows(BusinessException.class, () -> projetoService.atualizarStatusProjeto(1L, new AtualizarStatusDto(StatusProjeto.INICIADO)));
    }

    @Test
    void testAtualizarStatus_Falha_IniciarSemMembros() {
        projeto.setStatus(StatusProjeto.ANALISE_APROVADA);
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));

        assertThrows(BusinessException.class, () -> projetoService.atualizarStatusProjeto(1L, new AtualizarStatusDto(StatusProjeto.INICIADO)));
    }

    @Test
    void testAdicionarMembro_Falha_MaximoAtingido() {
        for(int i = 0; i < 10; i++) {
            projeto.getMembros().add(new Membro());
        }
        when(projetoRepository.findById(1L)).thenReturn(Optional.of(projeto));
        when(membroService.eFuncionario(1L)).thenReturn(membro);

        assertThrows(BusinessException.class, () -> projetoService.adicionarMembroAoProjeto(1L, 1L));
    }
}