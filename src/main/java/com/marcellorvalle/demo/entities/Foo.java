package com.marcellorvalle.demo.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
public class Foo {
    @Id
    @GeneratedValue
    private Long idFoo;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bar")
    @Fetch(FetchMode.JOIN)
    private Bar bar;
    private String name;

    public Long getIdFoo() {
        return idFoo;
    }

    public void setIdFoo(Long idFoo) {
        this.idFoo = idFoo;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
