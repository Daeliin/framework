package com.daeliin.components.domain.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Builds an valid url out of a String, by :
 * - deleting all non alphanumerical characters except '&'
 * - replacing whitespaces with a dash
 * - replacing & with a dash
 */
public class UrlFriendlyString {

    public final String value;
    
    public UrlFriendlyString(final String originalString) {
        String computedValue = "";

        if (StringUtils.isNotBlank(originalString)) {
            computedValue =
                originalString.toLowerCase()
                    .replaceAll("[^a-zA-Z0-9 &]", "")
                    .trim()
                    .replace(' ', '-')
                    .replace("&", "and");
        }

        value = computedValue;
    }
}
