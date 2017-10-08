package com.daeliin.components.webservices.validation;

import com.daeliin.components.core.pagination.PageRequest;
import com.daeliin.components.core.pagination.Sort;
import com.daeliin.components.webservices.exception.PageRequestException;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

import static java.util.stream.Collectors.toCollection;

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
            final String... properties) throws PageRequestException {
        this.page = Objects.requireNonNull(page);
        this.size = Objects.requireNonNull(size);
        this.direction = Objects.requireNonNull(direction);
        this.properties = Objects.requireNonNull(properties);
    }

    public PageRequest validate() throws PageRequestException {
        return new PageRequest(validateIndex(), validateSize(), validateSorts());
    }

    private int validateIndex() throws PageRequestException {
        try {
            int pageNumberValue = Integer.parseInt(page);

            if (pageNumberValue < 0) {
                throw new PageRequestException("Page number should be equal to or greater than 0");
            }

            return pageNumberValue;
        } catch (NumberFormatException e) {
            throw new PageRequestException("Page number should be an integer");
        }
    }
    
    private int validateSize() throws PageRequestException {
        try {
            int pageSizeValue = Integer.parseInt(size);

            if (pageSizeValue <= 0) {
                throw new PageRequestException("Page size should be greater than 0");
            }

            return pageSizeValue;
        } catch (NumberFormatException e) {
            throw new PageRequestException("Page size should be an integer");
        }
    }
    
    private LinkedHashSet<Sort> validateSorts() throws PageRequestException {
        Sort.Direction sortDirection;

        if ("asc".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.ASC;
        } else if ("desc".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.DESC;
        } else {
            throw new PageRequestException("Page direction must be 'asc' or 'desc'");
        }

        return Arrays.asList(properties)
                .stream()
                .map(property -> new Sort(property, sortDirection))
                .collect(toCollection(LinkedHashSet::new));
    }
}
