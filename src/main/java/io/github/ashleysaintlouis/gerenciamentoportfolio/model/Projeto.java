package io.github.ashleysaintlouis.gerenciamentoportfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataPrevisto;
    private LocalDate dataFim;
    private BigDecimal orcamento;

    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    @ManyToOne
    private Membro idResponsavel;

    @ManyToMany
    private List<Membro> membros = new ArrayList<>();
}
