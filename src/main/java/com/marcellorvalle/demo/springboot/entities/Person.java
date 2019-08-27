package com.marcellorvalle.demo.springboot.entities;

import com.marcellorvalle.demo.currency.Money;
import com.marcellorvalle.demo.currency.MoneyAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private char gender;
    @Convert(converter = MoneyAttributeConverter.class)
    private Money salary;
}
