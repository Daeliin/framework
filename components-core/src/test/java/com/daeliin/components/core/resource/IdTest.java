package com.daeliin.components.core.resource;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public final class IdTest {

    @Test
    public void shouldGenerateAnIdOfSize32() {
        String id = Id.random();
        assertThat(id.length()).isEqualTo(32);
    }

    @Test
    public void shouldGenerateAnAlphanumericId() {
        String id = Id.random();

        assertThat(id).matches("[a-zA-Z0-9]*");
    }

    @Test
    public void shouldGenerateUniqueIds() {
        int ITERATIONS = 100_000;
        Set<String> ids = new HashSet<>();

        for (int i = 0; i < ITERATIONS; i++) {
            ids.add(Id.random());
        }

        assertThat(ids).hasSize(ITERATIONS);
    }
}