package com.daeliin.framework.core.mail;

import com.daeliin.framework.core.resource.exception.MailBuildingException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * Represents a mail.
 * @see com.daeliin.framework.core.mail.Mail.MailBuilder
 */
public class Mail {
    
    private final String from;
    private final String to;
    private final String subject;
    private String templateName;
    private Map<String, String> parameters; 
    
    private Mail(
        final String from,
        final String to,
        final String subject,
        final String templateName,
        final Map<String, String> parameters) throws MailBuildingException {
        
        this.from = new EmailAddress(from).toString();
        this.to = new EmailAddress(to).toString();
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
    
    public Map<String, String> parameters() {
        return this.parameters;
    }
    
    private void buildTemplateName(final String templateName) throws MailBuildingException {
        if (StringUtils.isBlank(templateName)) {
            throw new MailBuildingException("Mail template name must not be blank");
        } else {
            this.templateName = templateName;
        }
    }
    
    private void buildParameters(final Map<String, String> parameters) {
        if (CollectionUtils.isEmpty(parameters)) {
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
     * Mail mail = Mail.builder().from("from@mail.com").to("to@mail.com").subject("Subject").templateName("template.html").noParameters().build();
     */
    public static class MailBuilder implements From, To, Subject, Parameters, TemplateName {
        private String from;
        private String to;
        private String subject;
        private String templateName;
        private Map<String, String> parameters; 

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
        public MailBuilder parameters(final Map<String, String> parameters) {
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
        MailBuilder parameters(final Map<String, String> parameters);
    }
}
