package com.marcellorvalle.demo.SpecificationTools;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Function;

public abstract class SpecExecLambdaMapping<T> {
    protected final Map<String, Function<List<String>, Predicate>> filterMap;
    private final List<Predicate> predicates;
    protected Root<T> root;
    protected CriteriaQuery query;
    protected CriteriaBuilder builder;
    private MultiValueMap<String, String> filter;

    public SpecExecLambdaMapping() {
        filterMap = new HashMap<>();
        predicates = new ArrayList<>();
    }

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
        final Optional<Function<List<String>, Predicate>> m = loadMethod(key);

        if (m.isPresent()) {
            predicates.add(m.get().apply(values));
        }
    }

    private Optional<Function<List<String>, Predicate>> loadMethod(String key) {
        if (filterMap.containsKey(key)) {
            return Optional.of(filterMap.get(key));
        }

        return Optional.empty();
    }
}
