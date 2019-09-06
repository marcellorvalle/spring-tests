package com.marcellorvalle.demo.playground;


import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EhCacheTests {
    @Test
    @Ignore
    public void testEviction() throws InterruptedException {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class, String.class, ResourcePoolsBuilder.heap(2))
                ).build();



        cacheManager.init();

        Cache<Long, String> cache = cacheManager.getCache("preConfigured", Long.class, String.class);

        cache.put(1L, "one");
        cache.put(2L, "two");
        Thread.sleep(1); //just to guarantee there is a different timestamp
        cache.get(1L);
        cache.put(3L, "three");

        List<String> inCache = StreamSupport
                .stream(cache.spliterator(), false)
                .map(Cache.Entry::getValue)
                .collect(Collectors.toList());

        Assertions.assertEquals(
                Arrays.asList("one", "three"),
                inCache
        );

        cacheManager.close();
    }
}
