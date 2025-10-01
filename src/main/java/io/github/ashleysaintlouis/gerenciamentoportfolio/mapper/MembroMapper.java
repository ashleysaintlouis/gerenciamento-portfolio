package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MembroMapper {
    MembroMapper INSTANCE = Mappers.getMapper(MembroMapper.class);
    @Mapping(target = "id", source = "idExterno")
    MembroExternalDto toMembroExternalDto(Membro membro);
    Membro toMembro(MembroRequestDto membroRequestDto);
    MembroResponseDto toMembroResponseDto(Membro membro);
    MembroDto toMembroDto(Membro membro);

}
