package com.blebail.components.core.string;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import static java.util.Objects.requireNonNull;

/**
 * Sanitizes a String by removing any HTML elements.
 */
public final class HtmlSanitizedString {

    public final String value;

    public HtmlSanitizedString(String originalString) {
        this(originalString, Whitelist.none());
    }

    public HtmlSanitizedString(String originalString, Whitelist whitelist) {
        requireNonNull(originalString);

        this.value = Jsoup.clean(originalString, whitelist);
    }
}
