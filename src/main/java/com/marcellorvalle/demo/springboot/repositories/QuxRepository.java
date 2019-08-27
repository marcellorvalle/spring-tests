package com.marcellorvalle.demo.springboot.repositories;

import com.marcellorvalle.demo.springboot.entities.Qux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuxRepository extends JpaRepository<Qux, Long> {
}
