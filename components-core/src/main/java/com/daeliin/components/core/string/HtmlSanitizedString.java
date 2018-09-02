package com.daeliin.components.core.string;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.Objects;

/**
 * Sanitizes a String by removing any HTML elements.
 */
public final class HtmlSanitizedString {

    public final String value;

    public HtmlSanitizedString(String originalString) {
        Objects.requireNonNull(originalString);

        this.value = Jsoup.clean(originalString, Whitelist.none());
    }

    public HtmlSanitizedString(String originalString, Whitelist whitelist) {
        Objects.requireNonNull(originalString);

        this.value = Jsoup.clean(originalString, whitelist);
    }
}
