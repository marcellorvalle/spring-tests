package com.marcellorvalle.demo.stream;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BatchCollectorBuilderTest {
    private final static int BATCH_SIZE = 4;

    @Test
    public void testBatchProcessing() {
        final List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<List<Integer>> chunks = numbers.stream()
                .collect(BatchCollectorBuilder.ofSize(BATCH_SIZE));

        Assertions.assertEquals(Arrays.asList(0, 1, 2, 3), chunks.get(0));
        Assertions.assertEquals(Arrays.asList(4, 5, 6, 7), chunks.get(1));
        Assertions.assertEquals(Arrays.asList(8, 9), chunks.get(2));
    }
}
