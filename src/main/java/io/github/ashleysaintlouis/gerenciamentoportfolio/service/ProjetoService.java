package io.github.ashleysaintlouis.gerenciamentoportfolio.service;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.*;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.BusinessException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.NotFoundException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.ProjetoMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatusProjeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.TipoClassificacao;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.ProjetoRepository;
import io.github.ashleysaintlouis.gerenciamentoportfolio.repository.ProjetoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private ProjetoMapper projetoMapper;
    @Autowired
    private MembroService membroService;

    // CRUD - Criar
    public ProjetoResponseDto salvarProjeto(ProjetoDto dto) {
        Membro responsavel = membroService.buscarMembroEntity(dto.responsavel());
        Projeto projeto = projetoMapper.toProjetoEntity(dto);
        projeto.setResponsavel(Long.valueOf(responsavel.getId()));
        projeto.setStatus(StatusProjeto.EM_ANALISE);
        Projeto salvo = projetoRepository.save(projeto);
        return criarResponseDto(salvo);
    }

    // CRUD - Atualizar
    public ProjetoResponseDto atualizarProjeto(Long id, ProjetoDto dto) {
        Projeto projeto = buscarProjetoPorId(id);
        Membro responsavel = membroService.buscarMembroEntity(dto.responsavel());

        projeto.setNome(dto.nome());
        projeto.setDescricao(dto.descricao());
        projeto.setDataInicio(dto.dataInicio());
        projeto.setDataPrevisto(dto.dataPrevisto());
        projeto.setOrcamento(dto.orcamento());
        projeto.setResponsavel(Long.valueOf(String.valueOf(responsavel)));

        Projeto salvo = projetoRepository.save(projeto);
        return criarResponseDto(salvo);
    }

    // CRUD - Buscar por ID
    public Projeto buscarProjetoPorId(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Projeto não encontrado: " + id));
    }

    public ProjetoResponseDto buscarProjetoDtoPorId(Long id) {
        return criarResponseDto(buscarProjetoPorId(id));
    }

    // CRUD - Listar com Filtro e Paginação
    public Page<ProjetoResponseDto> listarTodos(ProjetoFiltroDto filtro, Pageable pageable) {
        return projetoRepository.findAll(new ProjetoSpecification(filtro), pageable).map(this::criarResponseDto);
    }

    // CRUD - Excluir
    public void excluirProjeto(Long id) {
        Projeto projeto = buscarProjetoPorId(id);
        if (List.of(StatusProjeto.INICIADO, StatusProjeto.EM_ANDAMENTO, StatusProjeto.ENCERRADO).contains(projeto.getStatus())) {
            throw new BusinessException("Não é possível excluir projetos com status 'Iniciado', 'Em Andamento' ou 'Encerrado'.");
        }
        projetoRepository.delete(projeto);
    }

    // REGRAS DE NEGÓCIO
    public void adicionarMembroAoProjeto(Long membroId, Long projetoId) {
        Projeto projeto = buscarProjetoPorId(projetoId);
        Membro membro = membroService.eFuncionario(membroId);

        if (projeto.getMembros().size() >= 10) {
            throw new BusinessException("Não é possível adicionar mais de 10 membros ao projeto.");
        }

        long projetosAtivos = projetoRepository.countByMembrosIdAndStatusNotIn(membroId, List.of(StatusProjeto.ENCERRADO, StatusProjeto.CANCELADO));
        if (projetosAtivos >= 3) {
            throw new BusinessException("O membro já está alocado em 3 projetos ativos.");
        }

        if (projeto.getMembros().contains(membro)) {
            throw new BusinessException("Membro já associado a este projeto.");
        }

        projeto.getMembros().add(membro);
        projetoRepository.save(projeto);
    }

    public void atualizarStatusProjeto(Long id, AtualizarStatusDto dto) {
        Projeto projeto = buscarProjetoPorId(id);
        StatusProjeto novoStatus = dto.status();
        StatusProjeto atual = projeto.getStatus();

        if (novoStatus == StatusProjeto.CANCELADO) {
            projeto.setStatus(StatusProjeto.CANCELADO);
            projetoRepository.save(projeto);
            return;
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

        projetoRepository.save(projeto);
    }

    public RelatorioPortfolioDto gerarRelatorioPortfolio() {
        List<Projeto> projetos = projetoRepository.findAll();

        Map<StatusProjeto, Long> qtdProjetosPorStatus = projetos.stream()
                .collect(Collectors.groupingBy(Projeto::getStatus, Collectors.counting()));

        Map<StatusProjeto, BigDecimal> totalOrcadoPorStatus = projetos.stream()
                .collect(Collectors.groupingBy(Projeto::getStatus,
                        Collectors.reducing(BigDecimal.ZERO, Projeto::getOrcamento, BigDecimal::add)));

        double mediaDuracaoEncerrados = projetos.stream()
                .filter(p -> p.getStatus() == StatusProjeto.ENCERRADO && p.getDataFim() != null)
                .mapToLong(p -> ChronoUnit.DAYS.between(p.getDataInicio(), p.getDataFim()))
                .average()
                .orElse(0.0);

        long totalMembrosUnicos = projetoRepository.countDistinctMembros();

        return new RelatorioPortfolioDto(qtdProjetosPorStatus, totalOrcadoPorStatus, mediaDuracaoEncerrados, totalMembrosUnicos);
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