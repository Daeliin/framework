package com.daeliin.components.core.mail;

import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public final class MailTest {

    @Test
    public void shouldHaveABuilder() {
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