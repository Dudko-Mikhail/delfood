package by.dudko.webproject.controller.command;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.Router;
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

import java.util.HashMap;
import java.util.Map;

import static by.dudko.webproject.controller.RequestParameter.COMMAND;
import static by.dudko.webproject.controller.RequestParameter.EMAIL;

public class SendEmailVerificationMessageCommand implements Command {
    private final UserService userService = UserServiceImpl.getInstance();
    private final EmailService emailService = EmailServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException { // TODO rename
        String recipient = request.getParameter(RequestParameter.EMAIL);
        try { // TODO повторение кода с частью signUp command
            String verificationCode = userService.generateUserVerificationCodeByEmail(recipient);
            StringBuffer controllerUrl = request.getRequestURL();
            String link = getVerificationLink(controllerUrl.toString(), recipient, verificationCode);
            EmailTemplate authorizationTemplate = EmailTemplateType.getTemplateInstance(EmailTemplateType.AUTHORIZATION_TEMPLATE);
            authorizationTemplate.putParameterValue("verificationLink", link);
            emailService.sendEmailByTemplate(authorizationTemplate, recipient);
            request.setAttribute(RequestAttribute.EMAIL_TO_VERIFY, recipient);
            return new Router(Router.RouteType.FORWARD, PagePath.HOME_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Failed to send verification message", e);
        } catch (EmailException e) {
            throw new CommandException("Failed to send verification message", e);
        }
    }

    private String getVerificationLink(String controllerUrl, String email, String verificationCode) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(RequestParameter.VERIFICATION_CODE, verificationCode);
        queryParams.put(COMMAND, CommandType.CONFIRM_REGISTRATION.name());
        queryParams.put(EMAIL, email);
        return UrlRequestBuilder.buildRequestString(controllerUrl, queryParams);
    }
}
