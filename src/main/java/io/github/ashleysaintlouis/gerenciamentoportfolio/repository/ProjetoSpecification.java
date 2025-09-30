package io.github.ashleysaintlouis.gerenciamentoportfolio.repository;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.projeto.ProjetoFiltroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Projeto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProjetoSpecification implements Specification<Projeto> {

    private final ProjetoFiltroDto filtro;

    public ProjetoSpecification(ProjetoFiltroDto filtro) {
        this.filtro = filtro;
    }

    @Override
    public Predicate toPredicate(jakarta.persistence.criteria.Root<Projeto> root, jakarta.persistence.criteria.CriteriaQuery<?> query, jakarta.persistence.criteria.CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(filtro.nome())) {
            predicates.add(builder.like(builder.lower(root.get("nome")), "%" + filtro.nome().toLowerCase() + "%"));
        }
        if (filtro.status() != null) {
            predicates.add(builder.equal(root.get("status"), filtro.status()));
        }
        if (filtro.responsavelId() != null) {
            predicates.add(builder.equal(root.get("responsavel").get("id"), filtro.responsavelId()));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}