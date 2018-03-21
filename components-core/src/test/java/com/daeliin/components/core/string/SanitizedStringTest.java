package com.daeliin.components.core.string;

import org.jsoup.safety.Whitelist;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class SanitizedStringTest {

    @Test
    public void shouldRemoveScriptTag_byDefault() {
        String input = "Test<script>alert('ok');</script>";

        assertThat(new SanitizedString(input).value).isEqualTo("Test");
    }

    @Test
    public void shouldRemoveAnyHtmlTag_byDefault() {
        String input = "This<br> is <a href=\"www.google.com\">a</a> <strong>test</strong>";

        assertThat(new SanitizedString(input).value).isEqualTo("This is a test");
    }

    @Test
    public void shouldAllowRawLinks_byDefault() {
        String input = "https://www.google.com";

        assertThat(new SanitizedString(input).value).isEqualTo("https://www.google.com");
    }

    @Test
    public void shouldAllowHtmlLink_whenWhitelisted() {
        String input = "<a id=\"id\" class=\"link\" target=\"_blank\" href=\"https://www.google.com\">A link</a> and some text";

        assertThat(new SanitizedString(input, new Whitelist().addAttributes("a", "id", "class", "href", "target").addTags("a")).value).isEqualTo(input);
    }
}