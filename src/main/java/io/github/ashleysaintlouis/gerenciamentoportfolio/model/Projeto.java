package io.github.ashleysaintlouis.gerenciamentoportfolio.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Projeto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false)
    private LocalDate dataInicio;
    @Column(nullable = true)
    private LocalDate dataFim;
    @Column(nullable = false)
    private LocalDate dataPrevisto;
    @Column(nullable = false, scale = 2)
    private BigDecimal orcamento;
    @Column(nullable = true)
    private String descricao;
    @Column(unique = true)
    private Long responsavel;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProjeto status = StatusProjeto.EM_ANALISE;

    @ManyToMany
    private List<Membro> membros = new ArrayList<>();
}
