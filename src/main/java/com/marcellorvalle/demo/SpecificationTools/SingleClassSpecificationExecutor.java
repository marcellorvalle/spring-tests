package com.marcellorvalle.demo.SpecificationTools;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import java.util.function.Function;

public abstract class SingleClassSpecificationExecutor<T> {
    //repo.findAll(FilterFoo.apply(filtros))
    //private final Map<String, Function<List<String>, Predicate>> filterMap;
    private final List<Predicate> predicates;
    protected Root<T> root;
    protected CriteriaQuery query;
    protected CriteriaBuilder builder;
    private MultiValueMap<String, String> filter;


    public SingleClassSpecificationExecutor() {
        //filterMap = new HashMap<>();
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

//    protected void addFunction(String key, Function<List<String>, Predicate> function) {
//        filterMap.put(key, function);
//    }

    private Predicate getPredicate() {
        predicates.clear();
        filter.forEach(this::applyFilter);

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    private void applyFilter(String key, List<String> values) {
        Method[] methods = this.getClass().getMethods();
        Optional<Method> m = Arrays.stream(methods)
                .filter(method -> onlyTheCorrectMethod(method, key))
                .findFirst();

        if (m.isPresent()) {
            try {
                predicates.add((Predicate) m.get().invoke(values));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean onlyTheCorrectMethod(Method method, String name) {
        final Class<?>[] listParamter = {List.class};

        return method.getName().equals("listBy" + StringUtils.capitalize(name)) &&
                method.getParameterTypes().equals(listParamter) &&
                method.getReturnType().equals(Predicate.class);
    }
}
