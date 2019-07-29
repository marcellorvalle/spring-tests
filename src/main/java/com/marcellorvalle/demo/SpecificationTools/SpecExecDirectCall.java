package com.marcellorvalle.demo.SpecificationTools;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

public class SpecExecDirectCall<T> {
    private final List<Predicate> predicates = new ArrayList<>();
    protected Root<T> root;
    protected CriteriaQuery query;
    protected CriteriaBuilder builder;
    private MultiValueMap<String, String> filter;


    public final Specification<T> apply(MultiValueMap<String, String> filter) {
        return (root, query, criteriaBuilder) -> {
            this.root = root;
            this.query = query;
            this.builder = criteriaBuilder;
            this.filter = filter;

            return getPredicate();
        };
    }

    private Predicate getPredicate() {
        predicates.clear();
        filter.forEach(this::applyFilter);

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private void applyFilter(String key, List<String> values) {
        if (key.equals("name")) {
            predicates.add(filterByName(values));
        }

        if (key.equals("id")) {
            predicates.add(filterById(values));
        }
    }

    private Predicate filterByName(List<String> names) {
        return null;
    }

    private Predicate filterById(List<String> ids) {
        return null;
    }


}
