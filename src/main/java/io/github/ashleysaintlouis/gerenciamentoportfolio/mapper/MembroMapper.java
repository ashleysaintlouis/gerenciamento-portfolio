package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MembroMapper {

    MembroMapper INSTANCE = Mappers.getMapper(MembroMapper.class);

    MembroResponseDto toMembroResponseDto(Membro membro);
    Membro toMembroResponseEntity(MembroResponseDto dto);
    MembroDto toMembroDto(Membro membro);
    default MembroRequestDto toMembroDto(Long id, String nome, String cargo) {
        return new MembroRequestDto(id, nome, cargo);
    }
}
