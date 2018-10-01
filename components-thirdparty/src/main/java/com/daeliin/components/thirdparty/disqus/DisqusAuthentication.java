package com.daeliin.components.thirdparty.disqus;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class DisqusAuthentication {

    public final String apiKey;
    public final String apiSecret;
    public final String accessToken;

    public DisqusAuthentication(String apiKey, String apiSecret, String accessToken) {
        this.apiKey = Objects.requireNonNull(apiKey);
        this.apiSecret = Objects.requireNonNull(apiSecret);
        this.accessToken = Objects.requireNonNull(accessToken);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisqusAuthentication that = (DisqusAuthentication) o;

        if (apiKey != null ? !apiKey.equals(that.apiKey) : that.apiKey != null) return false;
        if (apiSecret != null ? !apiSecret.equals(that.apiSecret) : that.apiSecret != null) return false;
        return accessToken != null ? accessToken.equals(that.accessToken) : that.accessToken == null;
    }

    @Override
    public int hashCode() {
        int result = apiKey != null ? apiKey.hashCode() : 0;
        result = 31 * result + (apiSecret != null ? apiSecret.hashCode() : 0);
        result = 31 * result + (accessToken != null ? accessToken.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("apiKey", "***")
            .add("apiSecret", "***")
            .add("accessToken", "***")
            .toString();
    }
}
