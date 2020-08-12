package com.blebail.components.core.mail;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public final class MailTest {

    @Test
    public void shouldChackThatMailIsNotRaw() {
        Mail mail = Mail.builder()
                .from("tom.searle@architects.com")
                .to("to@gmail.Com")
                .subject("Contact Form")
                .templateName("hello")
                .parameters(Map.of("message", "Hello"))
                .build();

        assertThat(mail.isRaw()).isFalse();
    }

    @Test
    public void shouldChackThatMailIsRaw() {
        Mail mail = Mail.builder()
                .from("tom.searle@architects.com")
                .to("to@gmail.Com")
                .subject("Contact Form")
                .rawContent("<h1>Hello</h1>")
                .parameters(Map.of("message", "Hello"))
                .build();

        assertThat(mail.isRaw()).isTrue();
    }

    @Test
    public void shoulBuildWithRawContent() {
        Mail mail = Mail.builder()
                .from("tom.searle@architects.com")
                .to("to@gmail.Com")
                .subject("Contact Form")
                .rawContent("<h1>Hello</h1>")
                .parameters(Map.of("message", "Hello"))
                .build();

        assertThat(mail.from()).isEqualTo("tom.searle@architects.com");
        assertThat(mail.to()).isEqualTo("to@gmail.Com");
        assertThat(mail.subject()).isEqualTo("Contact Form");
        assertThat(mail.rawContent()).isEqualTo("<h1>Hello</h1>");
        assertThat(mail.parameters()).isEqualTo(Map.of("message", "Hello"));
    }

    @Test
    public void shoulBuildWithATemplateName() {
        Mail mail = Mail.builder()
                .from("tom.searle@architects.com")
                .to("to@gmail.Com")
                .subject("Contact Form")
                .templateName("contact")
                .parameters(Map.of("message", "Hello"))
                .build();

        assertThat(mail.from()).isEqualTo("tom.searle@architects.com");
        assertThat(mail.to()).isEqualTo("to@gmail.Com");
        assertThat(mail.subject()).isEqualTo("Contact Form");
        assertThat(mail.templateName()).isEqualTo("contact");
        assertThat(mail.parameters()).isEqualTo(Map.of("message", "Hello"));
    }

    @Test
    public void shouldHaveConsistentEqualsAndHashcode() {
        Mail mail1 = Mail.builder()
                .from("tom.searle@architects.com")
                .to("to@gmail.Com")
                .subject("Contact Form")
                .templateName("contact")
                .parameters(Map.of("message", "Hello"))
                .build();

        Mail mail2 = Mail.builder()
                .from("tom.searle@architects.com")
                .to("to@gmail.Com")
                .subject("Contact Form")
                .templateName("contact")
                .parameters(Map.of("message", "Hello"))
                .build();

        assertThat(mail1).isEqualTo(mail2);
        assertThat(mail1.hashCode()).isEqualTo(mail2.hashCode());
    }
}