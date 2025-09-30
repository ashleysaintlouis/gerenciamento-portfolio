package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MembroMapper {

    MembroMapper INSTANCE = Mappers.getMapper(MembroMapper.class);

    Membro toMembroEntity(MembroDto dto);

    MembroResponseDto toMembroResponseDto(Membro membro);
    Membro toMembroResponseEntity(MembroResponseDto dto);

    default MembroDto toMembroDto(Long id, String nome, String cargo) {
        return new MembroDto(nome, cargo);
    }
}
