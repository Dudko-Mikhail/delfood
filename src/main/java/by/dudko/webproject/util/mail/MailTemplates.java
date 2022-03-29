package by.dudko.webproject.util.mail;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.command.CommandType;

public class MailTemplates {
    public static final String CONFIRM_REGISTRATION = """
            <h1>Welcome to DelFood</h1>
            <hr/>
            <p>Please click the link below to verify your email address</p>
            <br/>
            <a href="%s">Verify email</a>
            <br/>
            <br/>
            <hr/>
            <p>All the best</p>
            <p>The Delfood company</p>
            """;
    // FIXME add template
    public static final String PASSWORD_RECOVERY_TEMPLATE = """
            <h1>Welcome to DelFood</h1>
            <hr/>
            <p>In order to restore your password please click the link below</p>
            <a href="%s">Restore password</a>
            <br/>
            <br/>
            <br/>
            <p>All the best</p>
            <p>The Delfood company</p>
            """;

    public static String compilePasswordRecoveryTemplate(StringBuffer controllerUrl, String recipient) { // Maybe class request builder
        String link = controllerUrl.append("?").append(RequestParameter.COMMAND)
                .append("=").append(CommandType.RESTORE_PASSWORD.name())
                .append("&").append(RequestParameter.EMAIL)
                .append("=").append(recipient)
                .toString();
        return String.format(PASSWORD_RECOVERY_TEMPLATE, link);
    }

    public static String compileConfirmRegistrationTemplate(StringBuffer controllerUrl, String recipient,
                                                            String verificationCode) {

        String link = controllerUrl.append("?").append(RequestParameter.COMMAND)
                .append("=").append(CommandType.CONFIRM_REGISTRATION.name())
                .append("&").append(RequestParameter.EMAIL)
                .append("=").append(recipient)
                .append("&").append(RequestParameter.VERIFICATION_CODE)
                .append("=").append(verificationCode)
                .toString();
        return String.format(CONFIRM_REGISTRATION, link);
    }
}
