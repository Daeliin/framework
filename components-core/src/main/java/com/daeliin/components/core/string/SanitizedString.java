package com.daeliin.components.core.string;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.Objects;

/**
 * Sanitizes a String by removing any HTML elements.
 */
public final class SanitizedString {

    public final String value;

    public SanitizedString(String originalString) {
        Objects.requireNonNull(originalString);

        this.value = Jsoup.clean(originalString, Whitelist.none());
    }
}
