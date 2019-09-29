package com.blebail.components.core.async;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public final class FuturesTest {

    @Test
    public void shouldReturnListOfValueFromListOfFutures_whenConcatAllAndAllAreSuccessful() throws Exception {
        List<CompletableFuture<String>> futures = new LinkedList<>();
        futures.add(CompletableFuture.completedFuture("1"));
        futures.add(CompletableFuture.completedFuture("2"));

        List<String> strings = Futures.concatAll(futures).get();

        assertThat(strings).containsExactly("1", "2");
    }

    @Test
    public void shouldReturnErrorFromListOfFutures_whenConcatAllAndAtLeastOneIsInError() {
        List<CompletableFuture<String>> futures = new LinkedList<>();
        futures.add(CompletableFuture.completedFuture("1"));
        futures.add(CompletableFuture.failedFuture(new IllegalArgumentException()));

        CompletableFuture<List<String>> allFutures = Futures.concatAll(futures);

        try {
            allFutures.get();
            fail("No future failed, one should have failed");
        } catch (Exception e) {
            assertThat(allFutures.isCompletedExceptionally()).isTrue();
            assertThat(e.getCause()).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    public void shouldReturnListOfValueFromListOfFutures_whenConcatAllSuccessfulAndAllAreSuccessful() throws Exception {
        List<CompletableFuture<String>> futures = new LinkedList<>();
        futures.add(CompletableFuture.completedFuture("1"));
        futures.add(CompletableFuture.completedFuture("2"));

        List<String> strings = Futures.concatOnlySuccessful(futures).get();

        assertThat(strings).containsExactly("1", "2");
    }

    @Test
    public void shouldReturnListOfValueFromListOfFutures_whenConcatAllSuccessfulAndAtLeastOneIsInError() throws Exception {
        List<CompletableFuture<String>> futures = new LinkedList<>();
        futures.add(CompletableFuture.completedFuture("1"));
        futures.add(CompletableFuture.failedFuture(new IllegalArgumentException()));
        futures.add(CompletableFuture.completedFuture("3"));

        List<String> strings = Futures.concatOnlySuccessful(futures).get();

        assertThat(strings).containsExactly("1", "3");
    }
}