package io.github.ashleysaintlouis.gerenciamentoportfolio.dto.usuario;


public record UsuarioDto(String email, String senha) {
    public UsuarioDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}
