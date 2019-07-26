package com.marcellorvalle.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Qux {
    @Id
    @GeneratedValue
    private Long idQux;
    private String name;

    public Long getIdQux() {
        return idQux;
    }

    public void setIdQux(Long idQux) {
        this.idQux = idQux;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
