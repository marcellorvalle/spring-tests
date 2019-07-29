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
    private MultiValueMap<String, String> filters;

    public SpecificationBuilder() {
        predicates = new ArrayList<>();
        clazz = this.getClass();
    }

    public final Specification<T> apply(MultiValueMap<String, String> filter) {
        return (root, query, criteriaBuilder) -> {
            this.root = root;
            this.query = query;
            this.builder = criteriaBuilder;
            this.filters = filter;

            return getPredicate();
        };
    }

    private Predicate getPredicate() {
        predicates.clear();
        filters.forEach(this::applyFilter);

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private void applyFilter(String key, List<String> values) {
        final Optional<Metadata> optMethod = MetadataTool.loadFilterMethod(key, clazz);

        if (optMethod.isPresent()) {
            try {
                Metadata metadata = optMethod.get();
                Method method = metadata.getMethod();
                Predicate predicate;

                if (metadata.parameterIsList()) {
                    predicate = (Predicate) method.invoke(this, values);
                } else {
                    predicate = (Predicate) method.invoke(
                            this,
                            StringParser.INSTANCE.parse(values.get(0), metadata.getParameterClass())
                    );
                }

                predicates.add(predicate);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
