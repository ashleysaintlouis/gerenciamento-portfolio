package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.controller.MembroExternalController;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.*;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.BusinessException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.NotFoundException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.MembroMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.ProjetoMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.TipoClassificacao;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.ProjetoRepository;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.ProjetoSpecification;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.client.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@Transactional
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private ProjetoMapper projetoMapper;
    @Autowired
    private ExternalApiService externalApiService;
    @Autowired
    private MembroService membroService;
    @Autowired
    private MembroMapper membroMapper;

    @Transactional
    public ProjetoResponseDto salvarProjeto(ProjetoRequestDto dto) throws JsonProcessingException {
        String idStr = String.valueOf(dto.idResponsavel());
        MembroExternalDto membrorequestExternal = externalApiService.getMembroId(idStr);

        if (membrorequestExternal.id() == null) {
            throw new NotFoundException("Membro não pode ser nulo : " + membrorequestExternal);
        }
        if (!membroService.eFuncionario(membrorequestExternal.cargo())){
            throw new BusinessException("Apenas membros com cargo 'Funcionario' podem ser associados a projetos.");
        }
        System.out.println("Passou da verificação eFuncionario!\n");

        if (eAlocadoNoMinimoTresProjeto(membrorequestExternal.id())){
            throw new BusinessException("O membro já está alocado em 3 projetos ativos.");
        }

        MembroResponseDto persistirMembro = membroService.criarMembro(membrorequestExternal);
        Projeto projeto = projetoMapper.toProjetoEntity(dto);
        projeto.setIdResponsavel(membroMapper.toMembro(persistirMembro));
        projeto.setStatus(StatusProjeto.EM_ANALISE);
        projeto.getMembros().add(membroMapper.toMembro(persistirMembro));
        Projeto salvo = projetoRepository.save(projeto);
        return criarResponseDto(salvo);
    }

    public ProjetoResponseDto atualizarProjeto(Long id, ProjetoRequestDto dto) {
        System.out.println("Atualizando projeto com o membro: \n" + id);
        Projeto projeto = buscarProjetoPorId(id);
        Membro responsavel = membroService.buscarMembroEntity(dto.idResponsavel());
        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setDataInicio(dto.dataInicio());
        projeto.setDataPrevisto(dto.dataPrevisto());
        projeto.setOrcamento(dto.orcamento());
        projeto.setIdResponsavel(responsavel);
        projeto.setMembros((List<Membro>) responsavel);
        Projeto salvo = projetoRepository.save(projeto);
        System.out.print("Projeto atualizado com sucesso! \n" + salvo);
        return criarResponseDto(salvo);
    }

    public Projeto buscarProjetoPorId(Long id) {
        System.out.println("Buscando projeto por id: " + id);
        Projeto idprojeto = projetoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado: " + id));
        System.out.println("Projeto após verificar" + idprojeto);
        return idprojeto;
    }

    public ProjetoResponseDto buscarProjetoDtoPorId(Long id) {
        return criarResponseDto(buscarProjetoPorId(id));
    }

    public Page<ProjetoResponseDto> listarTodos(ProjetoFiltroDto filtro, Pageable pageable) {
        System.out.println("Buscando todos projetos\n");
        return projetoRepository.findAll(new ProjetoSpecification(filtro), pageable).map(this::criarResponseDto);
    }

    public void excluirProjeto(Long id) {
        System.out.println("Excluindo projeto por id: \n" + id);
        Projeto projeto = buscarProjetoPorId(id);
        if (List.of(StatusProjeto.INICIADO, StatusProjeto.EM_ANDAMENTO, StatusProjeto.ENCERRADO).contains(projeto.getStatus())) {
            throw new BusinessException("Não é possível excluir projetos com status 'Iniciado', 'Em Andamento' ou 'Encerrado'.");
        }
        projetoRepository.delete(projeto);
    }

    public ProjetoResponseDto adicionarMembroAoProjeto(Long membroId, Long projetoId) {
        System.out.println("Adicionando membro ao projeto: \n" + membroId);
        Projeto projeto = buscarProjetoPorId(projetoId);
        System.out.println("Adicionando membro ao projeto membro: " + projeto);
        MembroExternalDto membrorequestExternal = externalApiService.getMembroId(String.valueOf(membroId));

        if (!verificarRegras(projeto, membrorequestExternal)) {
            throw new BusinessException("Não atende as regras de negócio.");
        }
        MembroResponseDto persistirMembro = membroService.criarMembro(membrorequestExternal);
        System.out.println("Membro criado com sucesso! \n" + persistirMembro);
        projeto.getMembros().add(membroMapper.toMembro(persistirMembro));
        System.out.println("Add membro na lista \n" );
        Projeto projetoSalvo = projetoRepository.save(projeto);
        return criarResponseDto(projetoSalvo);
    }

    private boolean verificarRegras(Projeto projeto, MembroExternalDto membroExternalDto) {
        System.out.println("Verificando as regras\n");
        if(!membroService.eFuncionario(membroExternalDto.cargo())) {
            throw new BusinessException("Membro " + membroExternalDto.cargo() + " não tem cargo funcionario.");
        }
        System.out.println("Passo eFuncionario");
        if (eJaAlocadoAoProjeto(membroExternalDto, projeto.getId())) {
            throw new BusinessException("Membro já associado a este projeto.");
        }
        System.out.println("Passo eJaAlocadoAoProjeto");
        if (eAlocadoNoMinimoTresProjeto(membroExternalDto.id())) {
            throw new BusinessException("O membro já está alocado em 3 projetos ativos.");
        }
        System.out.println("Passo eAlocadoNoMinimoTresProjeto");
        if (verificarQuantidadeDeMembros(projeto.getId())) {
            throw new BusinessException("Não é possível adicionar mais de 10 membros ao projeto.");
        }
        System.out.println("Passo verificarQuantidadeDeMembros");
        return true;
    }

    private boolean verificarQuantidadeDeMembros(Long projetoId) {
        Projeto projeto = buscarProjetoPorId(projetoId);
        return projeto.getMembros().size() > 10;
    }

    private boolean eAlocadoNoMinimoTresProjeto(Long membroId) {
        long projetosAtivos = projetoRepository.countByMembrosIdAndStatusNotIn(membroId, List.of(StatusProjeto.ENCERRADO, StatusProjeto.CANCELADO));
        return projetosAtivos > 3;
    }

    private boolean eJaAlocadoAoProjeto(MembroExternalDto membro,  Long projetoId) {
        Projeto projeto = buscarProjetoPorId(projetoId);
        MembroExternalDto membroAdd = membroService.buscarMembroExternalSeNaoExisteCriaNovo(membro);

        System.out.println("Passou busco");
        return projeto.getMembros().contains(membroMapper.toMembro(membroAdd));
    }


    public AtualizarStatusDto atualizarStatusProjeto(Long id, AtualizarStatusDto dto) {
        Projeto projeto = buscarProjetoPorId(id);
        StatusProjeto novoStatus = dto.status();
        StatusProjeto atual = projeto.getStatus();

        if (novoStatus == StatusProjeto.CANCELADO) {
            if (atual == StatusProjeto.ENCERRADO) {
                throw new BusinessException("Não pode cancelar projeto com status encerrado.");
            }
            projeto.setStatus(StatusProjeto.CANCELADO);
            Projeto status = projetoRepository.save(projeto);

            return projetoMapper.toAtualizarStatusDto(status);
        }

        if (novoStatus == StatusProjeto.INICIADO && projeto.getMembros().isEmpty()) {
            throw new BusinessException("O projeto deve ter pelo menos 1 membro alocado para ser iniciado.");
        }

        if (novoStatus.ordinal() != atual.ordinal() + 1) {
            throw new BusinessException("Transição de status inválida. Próximo status permitido: " + StatusProjeto.values()[atual.ordinal() + 1]);
        }

        projeto.setStatus(novoStatus);

        if (novoStatus == StatusProjeto.ENCERRADO) {
            projeto.setDataFim(LocalDate.now());
        }


        Projeto status = projetoRepository.save(projeto);
        return projetoMapper.toAtualizarStatusDto(status);
    }

    private TipoClassificacao calcularRisco(BigDecimal orcamento, LocalDate inicio, LocalDate previsto) {
        long meses = ChronoUnit.MONTHS.between(inicio, previsto);
        if (orcamento.compareTo(new BigDecimal("100000.00")) <= 0 && meses <= 3) return TipoClassificacao.BAIXO;
        if (orcamento.compareTo(new BigDecimal("500000.00")) > 0 || meses > 6) return TipoClassificacao.ALTO;
        return TipoClassificacao.MEDIO;
    }

    private ProjetoResponseDto criarResponseDto(Projeto projeto) {
        TipoClassificacao classificacao = calcularRisco(projeto.getOrcamento(), projeto.getDataInicio(), projeto.getDataPrevisto());
        return projetoMapper.toResponseDto(projeto, classificacao);
    }
}