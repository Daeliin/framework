package com.blebail.components.core.string;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.Objects;

/**
 * Sanitizes a String by removing any HTML elements by default,
 * or by providing a Whitelist
 */
public final class UserInputString {

    private final String inputString;

    private final Whitelist whitelist;

    public UserInputString(String inputString) {
        this(inputString, Whitelist.none());
    }

    public UserInputString(String inputString, Whitelist whitelist) {
        this.inputString = Objects.requireNonNull(inputString);
        this.whitelist = Objects.requireNonNull(whitelist);
    }

    public String sanitize() {
        return Jsoup.clean(inputString, whitelist);
    }
}
