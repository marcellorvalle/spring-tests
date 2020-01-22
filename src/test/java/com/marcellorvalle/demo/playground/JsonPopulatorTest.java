package com.marcellorvalle.demo.playground;

import com.marcellorvalle.demo.DemoApplicationTests;
import com.marcellorvalle.demo.TestConfiguration;
import com.marcellorvalle.demo.springboot.entities.Bar;
import com.marcellorvalle.demo.springboot.entities.Foo;
import com.marcellorvalle.demo.springboot.entities.Qux;
import com.marcellorvalle.demo.springboot.repositories.BarRepository;
import com.marcellorvalle.demo.springboot.repositories.FooRepository;
import com.marcellorvalle.demo.springboot.repositories.QuxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

//@DataJpaTest
@SpringBootTest
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = DemoApplicationTests.class)
public class JsonPopulatorTest {
    @Autowired
    FooRepository fooRepository;
    @Autowired
    BarRepository barRepository;
    @Autowired
    QuxRepository quxRepository;


    @Test
    public void testPopulate() {
        List<Foo> foos = fooRepository.findAll();
        List<Bar> bars = barRepository.findAll();
        List<Qux> quxes = quxRepository.findAll();
    }
}
