package com.daeliin.components.domain.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class IdTest {

    @Test
    public void shouldGenerateAnIdOfSize8() {
        String id = new Id().value;
        assertThat(id.length()).isEqualTo(8);
    }

    @Test
    public void shouldGenerateAnAlphanumericId() {
        String id = new Id().value;

        assertThat(id).matches("[a-zA-Z0-9]*");
    }
}