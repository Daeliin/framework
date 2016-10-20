package com.daeliin.components.core.utils;

import org.apache.commons.lang3.StringUtils;

public class UrlFriendlyString {

    private String value;
    
    public UrlFriendlyString(final String originalString) {
        value = "";
        
        if (!StringUtils.isBlank(originalString)) {
            value = 
                originalString.toLowerCase()
                    .replaceAll("[^a-zA-Z0-9 ]", "")
                    .trim()
                    .replace(' ', '-');
        } 
    }
    
    public String value() {
        return value;
    }
}
