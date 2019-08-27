package com.marcellorvalle.demo.data.specification;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Classe utilitária para conversão dos valores dos filtros (String) para outros tipos.
 */
public class StringParser {
    static final StringParser INSTANCE = new StringParser();

    private final Map<Class<?>, Function<String, ?>> parsers = new HashMap<>();

    private StringParser() {
        add(Object.class, obj -> obj);
        add(String.class, str -> str);
        add(Long.class, Long::valueOf);
        add(long.class, Long::valueOf);
        add(Integer.class, Integer::valueOf);
        add(int.class, Integer::valueOf);
        add(Double.class, Double::valueOf);
        add(double.class, Double::valueOf);
        add(Float.class, Float::valueOf);
        add(float.class, Float::valueOf);
        add(Character.class, str -> str.charAt(0));
        add(char.class, str -> str.charAt(0));
        add(Date.class, this::fromISO8601UTC);
    }

    /**
     * Permite a adição de parsers customizados, que serão usados na hora de converter o parâmetro recebido via url
     * para o tipo do parâmetro usado na função do filtro.
     */
    public <T> void add(Class<T> clazz, Function<String, T> parser) {
        parsers.put(clazz, parser);
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
