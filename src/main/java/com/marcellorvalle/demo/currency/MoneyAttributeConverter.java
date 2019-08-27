package com.marcellorvalle.demo.currency;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MoneyAttributeConverter implements AttributeConverter<Money, Double> {
    @Override
    public Double convertToDatabaseColumn(Money attribute) {
        return attribute.toDouble();
    }

    @Override
    public Money convertToEntityAttribute(Double dbData) {
        return Money.from(dbData);
    }
}
