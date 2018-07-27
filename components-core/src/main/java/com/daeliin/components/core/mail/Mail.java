package com.daeliin.components.core.mail;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * Represents a mail.
 * @see com.daeliin.components.core.mail.Mail.MailBuilder
 */
public class Mail {
    
    private final String from;
    private final String to;
    private final String subject;
    private final String templateName;
    private final Map<String, Object> parameters;
    
    private Mail(
        final String from,
        final String to,
        final String subject,
        final String templateName,
        final Map<String, Object> parameters) {
        
        this.from = new EmailAddress(from).value;
        this.to = new EmailAddress(to).value;
        this.subject = subject;

        if (StringUtils.isBlank(templateName)) {
            throw new IllegalArgumentException("Mail template name must not be blank");
        } else {
            this.templateName = templateName;
        }

        this.parameters = parameters;
    }
    
    public String from() {
        return this.from;
    }
    
    public String to() {
        return this.to;
    }
    
    public String subject() {
        return this.subject;
    }
    
    public String templateName() {
        return this.templateName;
    }
    
    public Map<String, Object> parameters() {
        return this.parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return Objects.equals(from, mail.from) &&
                Objects.equals(to, mail.to) &&
                Objects.equals(subject, mail.subject) &&
                Objects.equals(templateName, mail.templateName) &&
                Objects.equals(parameters, mail.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, subject, templateName, parameters);
    }

    public static From builder() {
        return new MailBuilder();
    }
    
    /**
     * Builds a mail, example : 
     * Mail mail = Mail.builder().from("from@mail.com").to("to@mail.com").subject("Subject").templateName("template.html").noParameters().build();
     */
    public static class MailBuilder implements From, To, Subject, Parameters, TemplateName {
        private String from;
        private String to;
        private String subject;
        private String templateName;
        private Map<String, Object> parameters;

        @Override
        public To from(final String from) {
            this.from = from;
            return this;
        }

        @Override
        public Subject to(final String to) {
            this.to = to;
            return this;
        }

        @Override
        public TemplateName subject(final String subject) {
            this.subject = subject;
            return this;
        }
        
        @Override
        public TemplateName noSubject() {
            this.subject = null;
            return this;
        }
        
        @Override
        public Parameters templateName(final String templateName) {
            this.templateName = templateName;
            return this;
        }
        
        @Override
        public MailBuilder noParameters() {
            this.parameters = null;
            return this;
        }
        
        @Override
        public MailBuilder parameters(final Map<String, Object> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Mail build() throws IllegalArgumentException {
            return new Mail(this.from, this.to, this.subject, this.templateName, this.parameters);
        }
    }
    
    public interface From {
        To from(final String from);
    }

    public interface To {
        Subject to(final String to);
    }

    public interface Subject {
        TemplateName subject(final String subject);
        TemplateName noSubject();
    }
    
    public interface TemplateName {
        Parameters templateName(final String templateName);
    }

    public interface Parameters {
        MailBuilder noParameters();
        MailBuilder parameters(final Map<String, Object> parameters);
    }
}
