package com.daeliin.components.thirdparty.google.search.custom;

import org.springframework.web.util.UriComponentsBuilder;

public final class GoogleApiUrlBuilder {

    private UriComponentsBuilder uriBuilder;

    public GoogleApiUrlBuilder() {
        this.uriBuilder = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("www.googleapis.com");
    }

    public GoogleApiUrlBuilder withPath(String path) {
        this.uriBuilder = this.uriBuilder.path("/" + path);

        return this;
    }

    public GoogleApiUrlBuilder withParam(String name, String value) {
        this.uriBuilder = this.uriBuilder.queryParam(name, value);

        return this;
    }

    public String build() {
        return this.uriBuilder.build()
            .encode()
            .toUriString();
    }
}