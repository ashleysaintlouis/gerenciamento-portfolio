package io.github.ashleysaintlouis.gerenciamentoportfolio.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @Column(nullable = false)
    private BigDecimal orcamento;
    @Column(nullable = true)
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "membros_projeto",
            joinColumns = @JoinColumn(name = "id_membro"),
            inverseJoinColumns = @JoinColumn(name = "id_projeto")
    )
    private Membro membro;
    @Column(nullable = false)
    private StatutProjeto statut;
}
