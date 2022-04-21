package by.dudko.webproject.util.mail;

import java.util.Set;

public enum EmailTemplateType {
    AUTHORIZATION_TEMPLATE("email-templates/confirm_registration.vm", "Authorization", Set.of("verificationLink"));

    private final String templateName;
    private final String subject;
    private final Set<String> templateParameters;

    EmailTemplateType(String templateName, String subject, Set<String> templateParameters) {
        this.templateName = templateName;
        this.subject = subject;
        this.templateParameters = templateParameters;
    }

    public static EmailTemplate getTemplateInstance(EmailTemplateType type) {
        return new EmailTemplate(type.templateName, type.subject, type.templateParameters);
    }
}
