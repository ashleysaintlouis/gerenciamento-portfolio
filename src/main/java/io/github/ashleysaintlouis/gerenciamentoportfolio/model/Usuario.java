package io.github.ashleysaintlouis.gerenciamentoportfolio.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 120)
    private String email;
    @Column(nullable = false)
    private String senha;
}
