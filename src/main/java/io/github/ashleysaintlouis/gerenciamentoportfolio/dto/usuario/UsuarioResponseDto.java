package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.usuario;

import lombok.Data;

public record UsuarioResponseDto (Long id, String email, String token) {
    public UsuarioResponseDto (Long id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }
}
