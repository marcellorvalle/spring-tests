package com.marcellorvalle.demo.data.specification;

import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * Classe utilitária para leitura de metadados de um SpecificationBuilder concreto.
 */
class MetadataTool {
    private MetadataTool() {
    }

    static Optional<Metadata> loadFilterMethod(String key, Class<? extends SpecificationBuilder> clazz) {
        final MetadataCache cache = MetadataCache.INSTANCE;
        final MetadataKey metadataKey = MetadataKey.create(key, clazz);

        if (!cache.contains(metadataKey)) {
            cache.put(metadataKey, loadMethodMetadata(key, clazz));
        }

        return cache.get(metadataKey);
    }

    private static Optional<Metadata> loadMethodMetadata(String key, Class<? extends SpecificationBuilder> clazz) {
        final Method[] methods = clazz.getDeclaredMethods();

        return Arrays.stream(methods)
                .filter(method -> onlyTheCorrectMethod(method, key))
                .map(Metadata::new)
                .findFirst();
    }

    private static boolean onlyTheCorrectMethod(Method method, String name) {
        return method.getName().equals("filterBy" + StringUtils.capitalize(name))
                && method.getParameterCount() == 1
                && method.getReturnType().equals(Predicate.class);
    }
}
