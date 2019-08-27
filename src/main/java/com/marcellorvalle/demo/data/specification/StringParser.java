package com.marcellorvalle.demo.data.specification;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe utilitária para conversão dos valores dos filtros (String) para outros tipos.
 */
class StringParser {
    static final StringParser INSTANCE = new StringParser();

    private Map<Class<?>, Function<String, ?>> parsers = new HashMap<>();

    private StringParser() {
        parsers.put(Object.class, obj -> obj);
        parsers.put(String.class, str -> str);
        parsers.put(Long.class, Long::valueOf);
        parsers.put(long.class, Long::valueOf);
        parsers.put(Integer.class, Integer::valueOf);
        parsers.put(int.class, Integer::valueOf);
        parsers.put(Double.class, Double::valueOf);
        parsers.put(double.class, Double::valueOf);
        parsers.put(Float.class, Float::valueOf);
        parsers.put(float.class, Float::valueOf);
        parsers.put(Character.class, str -> str.charAt(0));
        parsers.put(char.class, str -> str.charAt(0));
        parsers.put(Date.class, this::fromISO8601UTC);
    }

    <T> List<T> parseList(List<String> in, Class<T> forClass) {
        return in.stream()
                .map(str -> parse(str, forClass))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    <T> T parse(String in, Class<T> forClass) {
        if (!parsers.containsKey(forClass)) {
            throw new RuntimeException("Não existe parser para a classe " + forClass.getSimpleName());
        }

        return (T) parsers.get(forClass).apply(in);
    }

    private Date fromISO8601UTC(String dateStr) {
        return Date.from(Instant.parse(dateStr));
    }
}
