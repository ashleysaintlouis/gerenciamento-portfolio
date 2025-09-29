package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {StatusProjetoMapper.class})
public interface ProjetoMapper {
    Projeto toProjeto(ProjetoDto projetoDto);
    ProjetoDto toProjetoDto(Projeto projeto);
}
