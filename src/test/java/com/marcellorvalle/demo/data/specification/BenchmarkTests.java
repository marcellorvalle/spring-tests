package com.marcellorvalle.demo.data.specification;

import com.marcellorvalle.demo.SpecificationTools.SpecExecLambdaMapping;
import com.marcellorvalle.demo.springboot.entities.Foo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BenchmarkTests {
    @SuppressWarnings("unchecked")
    private final Root<Foo> root = (Root<Foo>) Mockito.mock(Root.class);
    private final CriteriaQuery query = Mockito.mock(CriteriaQuery.class);
    private final CriteriaBuilder builder = Mockito.mock(CriteriaBuilder.class, Mockito.withSettings().stubOnly());
    private final Logger logger = LoggerFactory.getLogger(BenchmarkTests.class);
    private final MultiValueMap<String, String> props = new LinkedMultiValueMap<>();

    public BenchmarkTests() {
        Mockito.when(builder.and()).thenReturn(null);
        Mockito.when(builder.and(Mockito.any())).thenReturn(null);

        props.add("name", "name");
        props.add("id", "2");
        props.add("foo", "foo");
        props.add("bar", "bar");
        props.add("qux", "qux");
        props.add("notExists", "value");
        props.add("notExists1", "value");
        props.add("notExists2", "value");
        props.add("notExists3", "value");
        props.add("notExists4", "value");
        props.add("notExists5", "value");
        props.add("notExists6", "value");
    }

    @Test
    public void benchmarkAll() {
        ConcreteReflective filterReflective = new ConcreteReflective();
        ConcreteLambdaMap filterLambdaMap = new ConcreteLambdaMap();

        runTests(filterReflective.apply(props), filterReflective.getClass().getName());
        runTests(filterLambdaMap.apply(props), filterLambdaMap.getClass().getName());
    }

    private void runTests(Specification<Foo> spec, String testName) {
        final int executions = 1000;
        final List<Long> values = new ArrayList<>();

        for (int j = 0; j < executions; j++) {
            Date start = new Date();
            for (int i = 0; i < 10000; i++) {
                findAll(spec);
            }
            Date end = new Date();

            values.add(end.getTime() - start.getTime());
        }

        double avg = values.stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(0.0);


        logger.info("{} - Average {} miliseconds.", testName, avg);
    }

    private void findAll(Specification<Foo> filter) {
        filter.toPredicate(root, query, builder);
    }

    public class ConcreteReflective extends SpecificationBuilder<Foo> {
        public Predicate filterByName(String name) {
            return null;
        }

        public Predicate filterById(Long id) {
            return null;
        }

        public Predicate filterByFoo(List<String> foo) {
            return null;
        }

        public Predicate filterByBar(List<String> bar) {
            return null;
        }

        public Predicate filterByQux(List<String> qux) {
            return null;
        }
    }

    private class ConcreteLambdaMap extends SpecExecLambdaMapping<Foo> {
        public ConcreteLambdaMap() {
            filterMap.put("name", this::filterByName);
            filterMap.put("id", this::filterById);
            filterMap.put("foo", this::filterByFoo);
            filterMap.put("bar", this::filterByBar);
            filterMap.put("qux", this::filterByQux);
        }

        public Predicate filterByName(List<String> names) {
           String name = names.get(0);

           return null;
        }

        public Predicate filterById(List<String> ids) {
            long id = Long.valueOf(ids.get(0));

            return null;
        }

        public Predicate filterByFoo(List<String> foo) {
            return null;
        }

        public Predicate filterByBar(List<String> bar) {
            return null;
        }

        public Predicate filterByQux(List<String> qux) {
            return null;
        }
    }
}
