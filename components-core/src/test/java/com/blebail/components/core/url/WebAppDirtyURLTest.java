package com.blebail.components.core.url;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public final class WebAppDirtyURLTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid",
            "http",
            "https://",
            "https://http://aaa",
    })
    public void shouldReturnFalse_whenUrlIsNotValid(String urlAsString) {
        assertThat(new WebAppDirtyURL(urlAsString).isValid()).isFalse();
    }
}