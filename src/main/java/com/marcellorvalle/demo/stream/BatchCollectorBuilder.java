package com.marcellorvalle.demo.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

/**
 * Collector para processar elementos de um stream em lotes.
 */
public class BatchCollectorBuilder {
    private BatchCollectorBuilder() {
    }

    public static <T> Collector<T, List<T>, List<List<T>>> ofSize(int size) {
        return Collector.of(
                ArrayList::new,
                List::add,
                (a, b) -> {
                    a.addAll(b);
                    return a;
                },
                a -> Partition.ofSize(a, size),
                Collector.Characteristics.UNORDERED
        );
    }

}
