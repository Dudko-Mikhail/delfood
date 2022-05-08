package by.dudko.webproject.controller.ajax.command.impl;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.ajax.command.ActionCommand;
import by.dudko.webproject.controller.command.CommandType;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.EmailException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.service.EmailService;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.EmailServiceImpl;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import by.dudko.webproject.util.UrlRequestBuilder;
import by.dudko.webproject.util.mail.EmailTemplate;
import by.dudko.webproject.util.mail.EmailTemplateType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static by.dudko.webproject.controller.RequestParameter.COMMAND;
import static by.dudko.webproject.controller.RequestParameter.EMAIL;

public class SendEmailVerificationMessageCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final EmailService emailService = EmailServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, CommandException {
        String recipient = request.getParameter(RequestParameter.EMAIL);
        try {
            String verificationCode = userService.generateUserVerificationCodeByEmail(recipient);
            String controllerUrl = getControllerUrl(request);
            String link = createVerificationLink(controllerUrl, recipient, verificationCode);
            EmailTemplate authorizationTemplate = EmailTemplateType.getTemplateInstance(EmailTemplateType.AUTHORIZATION_TEMPLATE);
            authorizationTemplate.putParameterValue("verificationLink", link);
            emailService.sendEmailByTemplate(authorizationTemplate, recipient);
        } catch (ServiceException | EmailException e) {
            logger.error("Failed to send verification email", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getControllerUrl(HttpServletRequest request) {
        String actionControllerUrl = request.getRequestURL().toString();
        return actionControllerUrl.replaceFirst("action", "controller");
    }

    private String createVerificationLink(String controllerUrl, String email, String verificationCode) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(RequestParameter.VERIFICATION_CODE, verificationCode);
        queryParams.put(COMMAND, CommandType.CONFIRM_REGISTRATION.name());
        queryParams.put(EMAIL, email);
        return UrlRequestBuilder.buildRequestString(controllerUrl, queryParams);
    }
}
