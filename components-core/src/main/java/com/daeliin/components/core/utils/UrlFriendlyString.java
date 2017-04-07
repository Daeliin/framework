package com.daeliin.components.core.utils;

import org.apache.commons.lang3.StringUtils;

public class UrlFriendlyString {

    public final String value;
    
    public UrlFriendlyString(final String originalString) {
        String computedValue = "";

        if (StringUtils.isNotBlank(originalString)) {
            computedValue =
                originalString.toLowerCase()
                    .replaceAll("[^a-zA-Z0-9 ]", "")
                    .trim()
                    .replace(' ', '-');
        }

        value = computedValue;
    }
}
