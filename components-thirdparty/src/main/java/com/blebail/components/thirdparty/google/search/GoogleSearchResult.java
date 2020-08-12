package com.blebail.components.thirdparty.google.search;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class GoogleSearchResult {

    public final String url;

    public GoogleSearchResult(String url) {
        this.url = Objects.requireNonNull(url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoogleSearchResult that = (GoogleSearchResult) o;

        return url != null ? url.equals(that.url) : that.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("url", url)
            .toString();
    }
}
