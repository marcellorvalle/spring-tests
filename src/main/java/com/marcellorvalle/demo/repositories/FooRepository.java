package com.marcellorvalle.demo.repositories;

import com.marcellorvalle.demo.entities.Foo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FooRepository extends JpaRepository<Foo, Long>, JpaSpecificationExecutor<Foo> {
    @Query("select f from Foo f inner join f.bar b inner join b.qux")
    public List<Foo> listAllFull();
}
