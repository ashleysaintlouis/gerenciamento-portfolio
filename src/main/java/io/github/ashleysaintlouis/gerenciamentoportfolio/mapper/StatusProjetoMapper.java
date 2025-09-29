package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.StatusDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.StatutProjeto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusProjetoMapper {
    StatutProjeto toEntityStatus(StatusDto statusDto);
    StatusDto toStatusDto(StatutProjeto statutProjeto);
}
