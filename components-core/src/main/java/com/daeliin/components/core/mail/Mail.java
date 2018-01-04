package com.daeliin.components.core.mail;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a mail.
 * @see com.daeliin.components.core.mail.Mail.MailBuilder
 */
public class Mail {
    
    private final String from;
    private final String to;
    private final String subject;
    private String templateName;
    private Map<String, Object> parameters;
    
    private Mail(
        final String from,
        final String to,
        final String subject,
        final String templateName,
        final Map<String, Object> parameters) throws MailBuildingException {
        
        this.from = new EmailAddress(from).value;
        this.to = new EmailAddress(to).value;
        this.subject = subject;
        buildTemplateName(templateName);
        buildParameters(parameters);
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
    
    private void buildTemplateName(final String templateName) throws MailBuildingException {
        if (StringUtils.isBlank(templateName)) {
            throw new MailBuildingException("Mail template name must not be blank");
        } else {
            this.templateName = templateName;
        }
    }
    
    private void buildParameters(final Map<String, Object> parameters) {
        if (parameters.isEmpty()) {
            this.parameters = new HashMap<>();
        } else {
            this.parameters = parameters;
        }
    }
    
    public static From builder() {
        return new MailBuilder();
    }
    
    /**
     * Builds a mail, example : 
     * Mail mail = Mail.builder().instantiate("instantiate@mail.com").to("to@mail.com").subject("Subject").templateName("template.html").noParameters().build();
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

        public Mail build() throws MailBuildingException {
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
