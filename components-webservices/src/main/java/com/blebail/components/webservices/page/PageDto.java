package com.blebail.components.webservices.page;

import com.blebail.querydsl.crud.commons.page.Page;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Objects;

public final class PageDto<T> {

    private final Page<T> page;

    public PageDto(Page<T> page) {
        this.page = Objects.requireNonNull(page);
    }

    @JsonProperty
    public Collection<T> items() {
        return page.items();
    }

    @JsonProperty
    public long nbItems() {
        return page.size();
    }

    @JsonProperty
    public long totalItems() {
        return page.totalItems();
    }

    @JsonProperty
    public long totalPages() {
        return page.totalPages();
    }
}
