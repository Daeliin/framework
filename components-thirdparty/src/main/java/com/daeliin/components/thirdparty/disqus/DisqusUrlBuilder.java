package com.daeliin.components.thirdparty.disqus;

import org.springframework.web.util.UriComponentsBuilder;

public final class DisqusUrlBuilder {

    private UriComponentsBuilder uriBuilder;

    public DisqusUrlBuilder() {
        this.uriBuilder = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("disqus.com");
    }

    public DisqusUrlBuilder withAuth(DisqusAuthentication auth) {
        this.uriBuilder = this.uriBuilder.queryParam("api_key", auth.apiKey)
            .queryParam("api_secret", auth.apiSecret)
            .queryParam("access_token", auth.accessToken);

        return this;
    }

    public DisqusUrlBuilder withPath(String path) {
        this.uriBuilder = this.uriBuilder.path("/api/3.0/" + path);

        return this;
    }

    public DisqusUrlBuilder withParam(String name, String value) {
        this.uriBuilder = this.uriBuilder.queryParam(name, value);

        return this;
    }

    public String build() {
        return this.uriBuilder.build()
            .encode()
            .toUriString();
    }
}
