package by.dudko.webproject.controller.command;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import by.dudko.webproject.util.mail.MailThreadFactory;
import jakarta.servlet.http.HttpServletRequest;

public class SendEmailVerificationMessageCommand implements Command {
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException { // TODO rename
        String recipient = request.getParameter(RequestParameter.EMAIL);
        try { // TODO повторение кода с частью signUp command
            String verificationCode = userService.generateUserVerificationCodeByEmail(recipient);
            StringBuffer controllerUrl = request.getRequestURL();
            Thread sender = MailThreadFactory.createConfirmRegistrationSender(controllerUrl, recipient, verificationCode); // TODO think about this thread
            sender.start();
            request.setAttribute(RequestAttribute.EMAIL_TO_VERIFY, recipient);
            return new Router(Router.RouteType.FORWARD, PagePath.HOME_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Failed to send verification message", e);
        }
    }
}
