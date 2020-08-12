package com.blebail.components.thirdparty.google.search;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public final class GoogleSearchQuery {

    private static final int DEFAULT_MAX_RESULTS = 10;

    public final String text;
    public final int maxResults;

    public GoogleSearchQuery(String text) {
        this(text, DEFAULT_MAX_RESULTS);
    }

    public GoogleSearchQuery(String text, int maxResults) {
        this.text = Objects.requireNonNull(text);
        this.maxResults = maxResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoogleSearchQuery that = (GoogleSearchQuery) o;

        if (maxResults != that.maxResults) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + maxResults;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("text", text)
            .add("maxResults", maxResults)
            .toString();
    }
}
