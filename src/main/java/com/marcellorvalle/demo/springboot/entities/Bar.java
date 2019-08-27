package com.marcellorvalle.demo.springboot.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Bar {
    @Id
    @GeneratedValue
    private Long idBar;
    @OneToOne
    private Qux qux;
    private String name;

    public Long getIdBar() {
        return idBar;
    }

    public void setIdBar(Long idBar) {
        this.idBar = idBar;
    }

    public Qux getQux() {
        return qux;
    }

    public void setQux(Qux qux) {
        this.qux = qux;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
