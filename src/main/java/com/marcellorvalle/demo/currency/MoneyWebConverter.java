package com.marcellorvalle.demo.currency;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MoneyWebConverter implements Converter<String, Money> {
    @Override
    public Money convert(String source) {
        return Money.from(source);
    }
}
