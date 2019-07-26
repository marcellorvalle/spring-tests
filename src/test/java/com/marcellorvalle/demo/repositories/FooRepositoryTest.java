package com.marcellorvalle.demo.repositories;

import com.marcellorvalle.demo.entities.Bar;
import com.marcellorvalle.demo.entities.Foo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Console;

@DataJpaTest
@RunWith(SpringRunner.class)
public class FooRepositoryTest {
    @Autowired
    private FooRepository fooRepository;

    @Autowired
    private BarRepository barRepository;

    @Test
    public void doTest() {
//        Bar bar = new Bar();
//        bar.setName("BAR-MRV");
//
//        barRepository.save(bar);
//
//        Foo foo = new Foo();
//
//
//        foo.setName("MRV");
//        foo.setBar(bar);
//
//        fooRepository.save(foo);

        fooRepository.listAllFull();
    }

}
