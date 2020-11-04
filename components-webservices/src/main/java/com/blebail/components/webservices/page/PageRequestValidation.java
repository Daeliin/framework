package com.blebail.components.webservices.page;


import com.blebail.querydsl.crud.commons.page.PageRequest;
import com.blebail.querydsl.crud.commons.page.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Validates parameters of a page request.
 */
public class PageRequestValidation {

    private final String page;
    private final String size;
    private final String direction;
    private final String[] properties;

    public PageRequestValidation(
            final String page,
            final String size,
            final String direction,
            final String... properties) {
        this.page = Objects.requireNonNull(page);
        this.size = Objects.requireNonNull(size);
        this.direction = Objects.requireNonNull(direction);
        this.properties = Objects.requireNonNull(properties);
    }

    public PageRequest validate() {
        return new PageRequest(validateIndex(), validateSize(), validateSorts());
    }

    private int validateIndex() {
        try {
            int pageNumberValue = Integer.parseInt(page);

            if (pageNumberValue < 0) {
                throw new IllegalArgumentException("Page number should be equal to or greater than 0");
            }

            return pageNumberValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Page number should be an integer");
        }
    }
    
    private int validateSize() {
        try {
            int pageSizeValue = Integer.parseInt(size);

            if (pageSizeValue <= 0) {
                throw new IllegalArgumentException("Page size should be greater than 0");
            }

            return pageSizeValue;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Page size should be an integer");
        }
    }
    
    private List<Sort> validateSorts() {
        Sort.Direction sortDirection;

        if ("asc".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.ASC;
        } else if ("desc".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.DESC;
        } else {
            throw new IllegalArgumentException("Page direction must be 'asc' or 'desc'");
        }

        return Arrays.stream(properties)
                .map(property -> new Sort(property, sortDirection))
                .collect(Collectors.toList());
    }
}
