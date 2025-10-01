package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.AtualizarStatusDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.TipoClassificacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjetoMapper {

    @Mapping(target = "idResponsavel", ignore = true)
    Projeto toProjetoEntity(ProjetoRequestDto dto);

    @Mapping(target = "classificacao", source = "classificacao")
    ProjetoResponseDto toResponseDto(Projeto projeto, TipoClassificacao classificacao);

    AtualizarStatusDto toAtualizarStatusDto(Projeto projeto);

}