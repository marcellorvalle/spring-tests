package com.marcellorvalle.demo.currency;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class MoneyAttributeConverter implements AttributeConverter<Money, Double> {
    @Override
    public Double convertToDatabaseColumn(Money attribute) {
        if (Objects.isNull(attribute))  {
            return null;
        }

        return attribute.toDouble();
    }

    @Override
    public Money convertToEntityAttribute(Double dbData) {
        if (Objects.isNull(dbData))  {
            return null;
        }

        return Money.from(dbData);
    }
}
