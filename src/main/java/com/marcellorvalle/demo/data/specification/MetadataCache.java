package com.marcellorvalle.demo.data.specification;

import java.util.*;

/**
 * Cache de metadados de reflexão para diminuir o overhead.
 *
 * Para cara filtro/builder(MetadataKey) acessado as informações de reflexão são armazenadas
 * diminuindo os custos nas próximas chamadas.
 */
class MetadataCache {
    static final MetadataCache INSTANCE = new MetadataCache();
    private final Map<MetadataKey, Optional<Metadata>> cache;

    private MetadataCache() {
        cache = new HashMap<>();
    }

    boolean contains(MetadataKey key) {
        return cache.containsKey(key);
    }

    void put(MetadataKey key, Optional<Metadata> lambda) {
        cache.put(key, lambda);
    }

    Optional<Metadata> get(MetadataKey key) {
        return cache.get(key);
    }
}
