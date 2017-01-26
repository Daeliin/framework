package com.daeliin.components.webservices.rest.controller;

import com.daeliin.components.webservices.exception.PageRequestException;
import org.springframework.data.domain.Sort;

/**
 * Builds parameters of a page request from their String value.
 */
public class PageRequestParameters {
    
    private int pageNumber;
    private int pageSize;
    private Sort.Direction direction;
    private String[] properties;

    public PageRequestParameters(
            final String pageNumberParameter, 
            final String pageSizeParameter, 
            final String directionParameter, 
            final String... propertiesParameter) throws PageRequestException{
        
        buildPageNumber(pageNumberParameter);
        buildPageSize(pageSizeParameter);
        buildDirection(directionParameter);
        buildProperties(propertiesParameter);
    }

    /**
     * Returns the page number.
     * @return the page number
     */
    public int pageNumber() {
        return pageNumber;
    }

    /**
     * Returns the page size.
     * @return the page size
     */
    public int pageSize() {
        return pageSize;
    }
    
    /**
     * Returns the sort direction.
     * @return the sort direction
     */
    public Sort.Direction direction() {
        return direction;
    }

    /**
     * Returns the array of property.
     * @return the array of property.
     */
    public String[] properties() {
        return properties;
    }
    
    private void buildPageNumber(final String pageNumberParameter) throws PageRequestException {
        boolean pageNumberParameterIsNotCorrect = false;
        
        try {
            int pageNumberValue = Integer.parseInt(pageNumberParameter);
            
            if (pageNumberValue < 0) {
                pageNumberParameterIsNotCorrect = true;
            }
            
            this.pageNumber = pageNumberValue;
        } catch (NumberFormatException e) {
            pageNumberParameterIsNotCorrect = true;
        }
        
        if (pageNumberParameterIsNotCorrect) {
            throw new PageRequestException("Page number must be an integer, equal to or greater than 0");
        }
    }
    
    private void buildPageSize(final String pageSizeParameter) throws PageRequestException {
        boolean pageSizeParameterIsNotCorrect = false;
        
        try {
            int pageSizeValue = Integer.parseInt(pageSizeParameter);
            
            if (pageSizeValue <= 0) {
                pageSizeParameterIsNotCorrect = true;
            }
            
            this.pageSize = pageSizeValue;
        } catch (NumberFormatException e) {
            pageSizeParameterIsNotCorrect = true;
        }
        
        if (pageSizeParameterIsNotCorrect) {
            throw new PageRequestException("Page size must be an integer, greater than 0");
        }
    }
    
    private void buildDirection(final String directionParameter) throws PageRequestException {
        if ("asc".equalsIgnoreCase(directionParameter)) {
            this.direction = Sort.Direction.ASC;
        } else if ("desc".equalsIgnoreCase(directionParameter)) {
            this.direction = Sort.Direction.DESC;
        } else {
            throw new PageRequestException("Page direction must be 'asc' or 'desc'");
        }
    }
    
    private void buildProperties(final String ... propertiesParameter) {
        this.properties = propertiesParameter;
    }
}
