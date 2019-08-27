package com.marcellorvalle.demo.SpecificationTools;

import com.marcellorvalle.demo.data.specification.SpecificationBuilder;
import com.marcellorvalle.demo.springboot.entities.Foo;

import javax.persistence.criteria.Predicate;
import java.util.List;

public class FooFilter extends SpecificationBuilder<Foo> {
//    protected void initialize() {
//        addFunction("name", this::filterByName);
//    }

    private Predicate filterByName(List<String> names) {
        return builder.equal(root.get("name"), names.get(0));


    }
}
