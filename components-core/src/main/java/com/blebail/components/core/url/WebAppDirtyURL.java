package com.blebail.components.core.url;

import org.apache.commons.validator.routines.UrlValidator;

import java.util.Objects;

public final class WebAppDirtyURL implements DirtyUrl {

    private static final UrlValidator URL_VALIDATOR = new UrlValidator(new String[]{"http", "https"});

    private final String urlAsString;

    public WebAppDirtyURL(String urlAsString) {
        this.urlAsString = Objects.requireNonNull(urlAsString);
    }

    @Override
    public boolean isValid() {
        return URL_VALIDATOR.isValid(urlAsString);
    }
}
