package com.blebail.components.core.async;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

public final class FuturesTest {

    @Test
    public void shouldReturnListOfValue_fromListOfFutures() throws Exception {
        List<CompletableFuture<String>> futures = new LinkedList<>();
        futures.add(CompletableFuture.completedFuture("1"));
        futures.add(CompletableFuture.completedFuture("2"));

        List<String> strings = Futures.allOf(futures).get();

        assertThat(strings).containsExactly("1", "2");
    }
}