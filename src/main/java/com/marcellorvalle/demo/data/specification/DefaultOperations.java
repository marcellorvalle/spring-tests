package com.marcellorvalle.demo.data.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Operações de comparação mais comuns
 * @param <T>
 */
public class DefaultOperations<T> {
    private final Root<T> root;
    private final CriteriaBuilder builder;

    DefaultOperations(Root<T> root, CriteriaBuilder builder) {
        this.root = root;
        this.builder = builder;
    }

    public <P> Predicate equal(String attribute, P value) {
        return builder.equal(root.get(attribute), value);
    }

    public Predicate like(String attribute, String value) {
        return builder.like(root.get(attribute), "%" + value + "%");
    }

    public <P> Predicate in(String attribute, List<P> values) {
        return root.get(attribute).in(values);
    }
}
