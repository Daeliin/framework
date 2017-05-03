package com.daeliin.components.webservices.rest.controller;

import com.daeliin.components.webservices.exception.PageRequestException;
import org.junit.Test;

public class PageRequestParametersTest {
    
    @Test(expected = PageRequestException.class)
    public void pageSize_zero_returnsOne() throws PageRequestException {
        PageRequestValidation pageRequestParameters = new PageRequestValidation("1", "0", "ASC", "id");
    }
}
