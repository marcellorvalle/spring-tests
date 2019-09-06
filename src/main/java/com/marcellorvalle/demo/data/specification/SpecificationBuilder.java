package com.marcellorvalle.demo.data.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Method;
import java.util.*;

public abstract class SpecificationBuilder<T> {
    private final List<Predicate> predicates;
    private final Class<? extends SpecificationBuilder> clazz;

    protected Root<T> root;
    protected CriteriaQuery query;
    protected CriteriaBuilder builder;

    private DefaultOperations<T> defaultOperations;
    private MultiValueMap<String, String> filters;

    public SpecificationBuilder() {
        predicates = new ArrayList<>();
        clazz = this.getClass();
    }

    /**
     * Permite a adição de parsers customizados
     */
    public static StringParser parameterParser() {
        return StringParser.INSTANCE;
    }

    public final Specification<T> apply(MultiValueMap<String, String> filter) {
        this.filters = filter;

        return (root, query, criteriaBuilder) -> {
            this.root = root;
            this.query = query;
            this.builder = criteriaBuilder;
            this.defaultOperations = new DefaultOperations<>(root, builder);

            return getPredicate();
        };
    }

    public DefaultOperations<T> byDefault() {
        return defaultOperations;
    }

    private Predicate getPredicate() {
        predicates.clear();
        filters.forEach(this::applyFilter);

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private void applyFilter(String key, List<String> values) {
        final Metadata metadata = MetadataTool.loadFilterMethod(key, clazz);

        if (metadata.isValidFilter()) {
            predicates.add(getPredicateFromMeta(metadata, values));
        }
    }

    private Predicate getPredicateFromMeta(Metadata metadata, List<String> values) {
        return metadata.parameterIsList() ?
                getPredicateFromListParameterMethod(metadata, values) :
                getPredicateFromSingleParameterMethod(metadata, values.get(0));
    }

    private Predicate getPredicateFromListParameterMethod(Metadata metadata, List<String> values) {
        return invoke(metadata.getMethod(),
                StringParser.INSTANCE.parseList(values, metadata.getParameterClass()));
    }

    private Predicate getPredicateFromSingleParameterMethod(Metadata metadata, String value) {
        return invoke(metadata.getMethod(),
                StringParser.INSTANCE.parse(value, metadata.getParameterClass()));
    }

    private Predicate invoke(Method method, Object... args) {
        try {
            return (Predicate) method.invoke(this, args);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
