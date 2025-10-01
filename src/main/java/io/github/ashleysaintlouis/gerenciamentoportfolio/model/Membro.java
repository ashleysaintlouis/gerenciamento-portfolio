package io.github.ashleysaintlouis.gerenciamentoportfolio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Membro)) return false;
        Membro membro = (Membro) o;
        return Objects.equals(id, membro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
