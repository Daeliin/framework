package com.daeliin.components.thirdparty.google.search.custom;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class GoogleApiUrlBuilderTest {

    @Test
    public void shouldBuildAnUrl_onSearchWithParam() {
        String url = new GoogleApiUrlBuilder()
            .withPath("customsearch/v1")
            .withParam("cx", "tomsearle")
            .build();

        assertThat(url).isEqualTo("https://www.googleapis.com/customsearch/v1?cx=tomsearle");
    }

    @Test
    public void shouldEncodeUrl() {
        String url = new GoogleApiUrlBuilder()
            .withPath("customsearch/v1")
            .withParam("cx", "tom searle")
            .build();

        assertThat(url).isEqualTo("https://www.googleapis.com/customsearch/v1?cx=tom%20searle");
    }
}