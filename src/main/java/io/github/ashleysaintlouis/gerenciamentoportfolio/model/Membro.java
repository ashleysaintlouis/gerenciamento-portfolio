package io.github.ashleysaintlouis.gerenciamentoportfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
public class Membro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_membro")
    private Long id;
    private Long idExterno;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cargo;

}
