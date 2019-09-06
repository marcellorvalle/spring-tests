package com.marcellorvalle.demo.data.specification;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.util.*;

/**
 * Cache de metadados de reflexão para diminuir o overhead.
 * <p>
 * Para cada filtro/builder(MetadataKey) acessado as informações de reflexão são armazenadas
 * diminuindo os custos nas próximas chamadas.
 */
class MetadataCache {
    static final MetadataCache INSTANCE = new MetadataCache();
    private static final int MAX_ELEMENTS = 500;
    private static final String CACHE_NAME = "metadata";

    private final Cache<MetadataKey, Metadata> cache;

    private MetadataCache() {
        cache = buildCache();
    }

    boolean contains(MetadataKey key) {
        return cache.containsKey(key);
    }

    void put(MetadataKey key, Metadata lambda) {
        cache.put(key, lambda);
    }

    Metadata get(MetadataKey key) {
        return cache.get(key);
    }

    private Cache<MetadataKey, Metadata> buildCache() {
        final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(CACHE_NAME,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                MetadataKey.class, Metadata.class, ResourcePoolsBuilder.heap(MAX_ELEMENTS))
                ).build(true);

        final Cache<MetadataKey, Metadata> result =
                cacheManager.getCache(CACHE_NAME, MetadataKey.class, Metadata.class);

        return result;
    }
}
