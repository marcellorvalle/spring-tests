package com.marcellorvalle.demo.SpecificationTools;

import com.marcellorvalle.demo.entities.Foo;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class FooFilter extends SingleClassSpecificationExecutor<Foo> {
//    protected void initialize() {
//        addFunction("name", this::filterByName);
//    }

    private Predicate filterByName(List<String> names) {
        return builder.equal(root.get("name"), names.get(0));


    }
}
