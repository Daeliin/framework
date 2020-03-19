package com.blebail.components.core.string;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.Objects;

/**
 * Sanitizes a string coming from a web app by removing any HTML and JS elements by default,
 * or by providing a specific Whitelist
 */
public final class WebAppDirtyString implements DirtyString {

    private final String inputString;

    private final Whitelist whitelist;

    public WebAppDirtyString(String inputString) {
        this(inputString, Whitelist.none());
    }

    public WebAppDirtyString(String inputString, Whitelist whitelist) {
        this.inputString = Objects.requireNonNull(inputString);
        this.whitelist = Objects.requireNonNull(whitelist);
    }

    @Override
    public String sanitize() {
        return Jsoup.clean(inputString, whitelist);
    }
}
