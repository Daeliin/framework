package com.daeliin.components.thirdparty.google.search.html;

import com.daeliin.components.thirdparty.google.search.html.GoogleUrlBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class GoogleUrlBuilderTest {

    @Test
    public void shouldBuildAnUrl_onSearchWithParam() {
        String url = new GoogleUrlBuilder()
            .withPath("search")
            .withParam("q", "test")
            .build();

        assertThat(url).isEqualTo("https://google.com/search?q=test");
    }

    @Test
    public void shouldEncodeUrl() {
        String url = new GoogleUrlBuilder()
            .withPath("search")
            .withParam("q", "a test")
            .build();

        assertThat(url).isEqualTo("https://google.com/search?q=a%20test");
    }
}