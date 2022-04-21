package by.dudko.webproject.util.mail;

import by.dudko.webproject.exception.EmailException;
import org.apache.velocity.VelocityContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class EmailTemplate {
    private final String templateName;
    private final String subject;
    private final Map<String, Object> model;

    EmailTemplate(String templateName, String subject, Set<String> parameterNames) {
        this.templateName = templateName;
        this.subject = subject;
        model = new HashMap<>();
        parameterNames.forEach(param -> model.put(param, null));
    }

    public void putParameterValue(String parameterName, Object value) throws EmailException {
        if (model.containsKey(parameterName)) {
            model.put(parameterName, value);
        } else {
            throw new EmailException("There is no parameter named " + parameterName);
        }
    }

    public boolean isConfigured() {
        return model.values()
                .stream()
                .noneMatch(Objects::isNull);
    }

    public VelocityContext getVelocityContext() {
        return new VelocityContext(model);
    }

    public Set<String> getParameterNames() {
        return Collections.unmodifiableSet(model.keySet());
    }

    public String getSubject() {
        return subject;
    }

    public String getTemplateName() {
        return templateName;
    }
}

