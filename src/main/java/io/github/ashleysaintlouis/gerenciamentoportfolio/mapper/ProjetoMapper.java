package io.github.ashleysaintlouis.gerenciamentoportfolio.mapper;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.TipoClassificacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjetoMapper {

    @Mapping(target = "responsavel", ignore = true)
    Projeto toProjetoEntity(ProjetoDto dto);

    /**
     * Mapeia a entidade Projeto para o DTO de resposta.
     * O 'source' "responsavel.id" informa ao MapStruct para buscar o campo 'id'
     * dentro do objeto 'responsavel' que está no 'projeto'.
     */
    @Mapping(target = "classificacao", source = "classificacao")
    ProjetoResponseDto toResponseDto(Projeto projeto, TipoClassificacao classificacao);
}