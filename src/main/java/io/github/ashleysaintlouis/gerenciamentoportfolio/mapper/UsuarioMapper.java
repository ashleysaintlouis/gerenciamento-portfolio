package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.usuario.UsuarioDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.usuario.UsuarioResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDto usuarioDto);
    UsuarioDto toDto(Usuario usuario);
    UsuarioResponseDto toResponseDto(Usuario usuario);
}
