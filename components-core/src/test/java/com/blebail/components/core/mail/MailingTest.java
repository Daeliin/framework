package com.blebail.components.core.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class MailingTest {

    private JavaMailSender javaMailSenderMock;

    private ITemplateEngine templateEngineMock;

    private Mailing tested;

    @BeforeEach
    void setUp() {
        templateEngineMock = mock(ITemplateEngine.class);
        javaMailSenderMock = mock(JavaMailSender.class);

        doReturn("<div>processed</div>").when(templateEngineMock).process(anyString(), any(Context.class));
        doReturn(new JavaMailSenderImpl().createMimeMessage()).when(javaMailSenderMock).createMimeMessage();

        tested = new Mailing(templateEngineMock, javaMailSenderMock);
    }

    @Test
    void shouldRenderAndSendTemplatedMail() {
        Mail mail = new Mail.MailBuilder()
                .from("from@blebail.com")
                .to("to@blebail.com")
                .subject("Subject")
                .templateName("myTemplate")
                .noParameters()
                .build();

        tested.send(mail);

        verify(templateEngineMock).process(eq("myTemplate"), any(Context.class));
        verify(javaMailSenderMock).send(any(MimeMessage.class));
    }

    @Test
    void shouldSendRawMail() {
        Mail mail = new Mail.MailBuilder()
                .from("from@blebail.com")
                .to("to@blebail.com")
                .subject("Subject")
                .rawContent("<h1>Ok</h1>")
                .build();

        tested.send(mail);

        verify(javaMailSenderMock).send(any(MimeMessage.class));
    }
}