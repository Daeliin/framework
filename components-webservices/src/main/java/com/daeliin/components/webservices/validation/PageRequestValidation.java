package com.daeliin.components.webservices.validation;

import com.daeliin.components.core.pagination.Sort;
import com.daeliin.components.webservices.exception.PageRequestException;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static java.util.stream.Collectors.toCollection;

/**
 * Validates parameters of a page request.
 */
public class PageRequestValidation {
    
    public int index;
    public int size;
    public LinkedHashSet<Sort> sorts;

    public PageRequestValidation(
            final String index,
            final String size,
            final String direction,
            final String... properties) throws PageRequestException {
        
        this.index = validateIndex(index);
        this.size = validateSize(size);
        this.sorts = validateSorts(direction, properties);
    }

    private int validateIndex(final String index) throws PageRequestException {
        try {
            int pageNumberValue = Integer.parseInt(index);
            
            if (pageNumberValue < 0) {
                throw new PageRequestException("Page number should be equal to or greater than 0");
            }
            
            return pageNumberValue;
        } catch (NumberFormatException e) {
            throw new PageRequestException("Page number should be an integer");
        }
    }
    
    private int validateSize(final String pageSizeParameter) throws PageRequestException {
        try {
            int pageSizeValue = Integer.parseInt(pageSizeParameter);
            
            if (pageSizeValue <= 0) {
                throw new PageRequestException("Page size should be greater than 0");
            }
            
            return pageSizeValue;
        } catch (NumberFormatException e) {
            throw new PageRequestException("Page size should be an integer");
        }
    }
    
    private LinkedHashSet<Sort> validateSorts(String direction, String[] properties) throws PageRequestException {
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
