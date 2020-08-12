package com.blebail.components.core.string;

@FunctionalInterface
public interface DirtyString {

    /**
     * Sanitizes the string according to whatever strategy
     * @return the sanitized string
     */
    String sanitize();
}
