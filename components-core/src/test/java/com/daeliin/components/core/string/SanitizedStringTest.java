package com.daeliin.components.core.string;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class SanitizedStringTest {

    @Test
    public void shouldRemove_scriptTag() {
        String input = "Test<script>alert('ok');</script>";

        assertThat(new SanitizedString(input).value).isEqualTo("Test");
    }

    @Test
    public void shouldRemove_anyHtmlTag() {
        String input = "This<br> is <a href=\"www.google.com\">a</a> <strong>test</strong>";

        assertThat(new SanitizedString(input).value).isEqualTo("This is a test");
    }
}