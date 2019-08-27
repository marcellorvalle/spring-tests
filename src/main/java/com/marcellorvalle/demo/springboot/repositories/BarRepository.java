package com.marcellorvalle.demo.springboot.repositories;

import com.marcellorvalle.demo.springboot.entities.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {
}
