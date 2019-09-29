package com.blebail.components.core.async;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public final class Futures {

    public static <T> CompletableFuture<List<T>> concatAll(List<CompletableFuture<T>> futuresList) {
        CompletableFuture<Void> allFuturesResult =
                CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));

        return allFuturesResult.thenApply(v ->
                futuresList.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.toList())
        );
    }

    public static <T> CompletableFuture<List<T>> concatOnlySuccessful(List<CompletableFuture<T>> futuresList) {
        List<T> results = new LinkedList<>();

        for (CompletableFuture<T> future : futuresList) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                continue;
            }
        }

        return CompletableFuture.completedFuture(results);
    }
}
