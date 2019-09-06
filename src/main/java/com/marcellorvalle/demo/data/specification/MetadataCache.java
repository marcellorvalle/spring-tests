package com.marcellorvalle.demo.data.specification;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.util.Objects;

/**
 * Cache de metadados de reflexão para diminuir o overhead.
 * <p>
 * Para cada filtro/builder(MetadataKey) acessado as informações de reflexão são armazenadas
 * diminuindo os custos nas próximas chamadas.
 */
public class MetadataCache {
    static final MetadataCache INSTANCE = new MetadataCache();
    private static final int MAX_ELEMENTS = 500;
    private static final String CACHE_NAME = "filter_MetadataCache";

    private final Cache<MetadataKey, Metadata> cache;

    private CacheManager cacheManager;

    private MetadataCache() {
        cache = buildCache();
    }

    /**
     * Injeção de um CacheManager externo
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
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
        getCacheManager().init();

        return getCacheManager().getCache(CACHE_NAME, MetadataKey.class, Metadata.class);
    }

    private CacheManager getCacheManager() {
        if (Objects.isNull(cacheManager)) {
            cacheManager = buildCacheManager();
        }

        return cacheManager;
    }

    private CacheManager buildCacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(CACHE_NAME,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                MetadataKey.class, Metadata.class, ResourcePoolsBuilder.heap(MAX_ELEMENTS))
                ).build();
    }
}
