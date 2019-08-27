package com.marcellorvalle.demo.data.specification;

import com.marcellorvalle.demo.springboot.entities.Person;
import com.marcellorvalle.demo.springboot.repositories.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class IntegrationTest {
    private final PersonRepository personRepository;
    private final PersonFilter filter;

    @Autowired
    public IntegrationTest(PersonRepository personRepository) {
        this.personRepository = personRepository;
        filter = new PersonFilter();
        populateDb();
    }

    @Test
    public void testFilterByName() {
        List<Person> result = personRepository.findAll(
                filter.apply(filterByNames("Holden", "Naomi"))
        );

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Holden", result.get(0).getName());
        Assertions.assertEquals("Naomi", result.get(1).getName());
    }

    @Test
    public void testFilterByGender() {
        List<Person> result = personRepository.findAll(
                filter.apply(filterByGender('F'))
        );

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Naomi", result.get(0).getName());
        Assertions.assertEquals("Avassalara", result.get(1).getName());
    }

    @Test
    public void testFilterByGenderAndName() {
        MultiValueMap<String, String> filterProps = filterByGender('M');
        filterProps.add("nameContains", "A");

        List<Person> result = personRepository.findAll(
                filter.apply(filterProps)
        );

        Assertions.assertEquals(2, result.size());
    }

    private void populateDb() {
        personRepository.save(Person.builder()
                .name("Holden")
                .gender('M')
                .build());

        personRepository.save(Person.builder()
                .name("Naomi")
                .gender('F')
                .build());

        personRepository.save(Person.builder()
                .name("Amos")
                .gender('M')
                .build());

        personRepository.save(Person.builder()
                .name("Alex")
                .gender('M')
                .build());

        personRepository.save(Person.builder()
                .name("Avassalara")
                .gender('F')
                .build());
    }

    private MultiValueMap<String, String> filterByNames(String... names) {
        return filterByProps("name", new LinkedMultiValueMap<>(), names);
    }

    private MultiValueMap<String, String> filterByGender(char gender) {
        return filterByProps("gender", new LinkedMultiValueMap<>(), String.valueOf(gender));
    }

    private MultiValueMap<String, String> filterByProps(
            String key, MultiValueMap<String, String> actual, String... props) {
        Arrays.stream(props)
                .forEach(name -> actual.add(key, name));

        return actual;
    }

    public class PersonFilter extends SpecificationBuilder<Person> {
        public Predicate filterByName(List<String> names) {
            return byDefault().in("name", names);
        }

        public Predicate filterByNameContains(String name) {
            return byDefault().like("name", name);
        }

        public Predicate filterByGender(char gender) {
            return byDefault().equal("gender", gender);
        }
    }

}
