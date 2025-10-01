package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MembroMapper {
    MembroMapper INSTANCE = Mappers.getMapper(MembroMapper.class);
    @Mapping(target = "id", source = "idExterno")
    MembroExternalDto toMembroExternalDto(Membro membro);
    Membro toMembro(MembroExternalDto membroExternalDto);
    MembroResponseDto toMembroResponseDto(Membro membro);
    Membro toMembro(MembroResponseDto membroResponseDto);
    List<MembroResponseDto> toMembro(List<Membro> membros);
}
