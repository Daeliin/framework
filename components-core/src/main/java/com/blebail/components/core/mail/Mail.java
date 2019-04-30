package com.blebail.components.core.mail;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a mail.
 * @see com.blebail.components.core.mail.Mail.MailBuilder
 */
public class Mail {
    
    private final String from;

    private final String to;

    private final String subject;

    private final String templateName;

    private final String rawContent;

    private final Map<String, Object> parameters;
    
    private Mail(
        final String from,
        final String to,
        final String subject,
        final String templateName,
        final String rawContent,
        final Map<String, Object> parameters) {
        
        this.from = new EmailAddress(from).value;
        this.to = new EmailAddress(to).value;
        this.subject = subject;

        if (StringUtils.isBlank(templateName) && StringUtils.isBlank(rawContent) ) {
            throw new IllegalArgumentException("Mail should have either a template name or raw content");
        }

        this.templateName = templateName;
        this.rawContent = rawContent;

        this.parameters = parameters;
    }

    public boolean isRaw() {
        return rawContent != null;
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

    public String rawContent() {
        return this.rawContent;
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
                Objects.equals(rawContent, mail.rawContent) &&
                Objects.equals(parameters, mail.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, subject, templateName, rawContent, parameters);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("from", from)
                .add("to", to)
                .add("subject", subject)
                .add("templateName", templateName)
                .add("rawContent", rawContent)
                .add("parameters", parameters)
                .toString();
    }

    public static From builder() {
        return new MailBuilder();
    }

    public static class MailBuilder implements From, To, Subject, Parameters, Content {
        private String from;

        private String to;

        private String subject;

        private String templateName;

        private String rawContent;

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
        public Content subject(final String subject) {
            this.subject = subject;
            return this;
        }
        
        @Override
        public Content noSubject() {
            this.subject = null;
            return this;
        }
        
        @Override
        public Parameters templateName(final String templateName) {
            this.templateName = templateName;
            return this;
        }

        @Override
        public MailBuilder rawContent(String rawContent) {
            this.rawContent = rawContent;
            return this;
        }

        @Override
        public MailBuilder noParameters() {
            this.parameters = new HashMap<>();
            return this;
        }
        
        @Override
        public MailBuilder parameters(final Map<String, Object> parameters) {
            this.parameters = parameters;
            return this;
        }

        public Mail build() throws IllegalArgumentException {
            return new Mail(this.from, this.to, this.subject, this.templateName, this.rawContent, this.parameters);
        }
    }
    
    public interface From {
        To from(final String from);
    }

    public interface To {
        Subject to(final String to);
    }

    public interface Subject {
        Content subject(final String subject);

        Content noSubject();
    }
    
    public interface Content {
        Parameters templateName(final String templateName);

        MailBuilder rawContent(final String rawContent);
    }

    public interface Parameters {
        MailBuilder noParameters();

        MailBuilder parameters(final Map<String, Object> parameters);
    }
}
