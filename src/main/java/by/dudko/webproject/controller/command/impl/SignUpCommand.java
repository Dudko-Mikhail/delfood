package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import by.dudko.webproject.util.mail.MailThreadFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static by.dudko.webproject.controller.RequestParameter.LOGIN;
import static by.dudko.webproject.controller.RequestParameter.EMAIL;
import static by.dudko.webproject.controller.RequestParameter.PASSWORD;
import static by.dudko.webproject.controller.RequestParameter.REPEAT_PASSWORD;
import static by.dudko.webproject.controller.RequestParameter.PHONE_NUMBER;
import static by.dudko.webproject.controller.RequestParameter.FIRST_NAME;
import static by.dudko.webproject.controller.RequestParameter.LAST_NAME;

public class SignUpCommand implements Command {
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HashMap<String, String> userData = new HashMap<>();
        String email = request.getParameter(EMAIL);
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(EMAIL, email);
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(REPEAT_PASSWORD, request.getParameter(REPEAT_PASSWORD));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        userData.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userData.put(LAST_NAME, request.getParameter(LAST_NAME));
        HttpSession session = request.getSession();
        try {
            Map<String, String> validationReport = userService.signUp(userData);
            boolean isRegistered = validationReport.isEmpty();
            if (isRegistered) { // TODO повтор кода (sendEmailVerificationMessageCommand)
                String verificationCode = userService.generateUserVerificationCodeByEmail(email);
                StringBuffer controllerUrl = request.getRequestURL();
                Thread sender = MailThreadFactory.createConfirmRegistrationSender(controllerUrl, email, verificationCode); // TODO Think about this thread
                sender.start();
                request.setAttribute(RequestAttribute.EMAIL_TO_VERIFY, email);
            }
            else {
                userData.forEach(request::setAttribute);
                validationReport.forEach(request::setAttribute);
            }
            request.setAttribute(RequestAttribute.SIGN_UP_RESULT, isRegistered);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute SignUp command", e);
        }
        String currentPage = (String) session.getAttribute(SessionAttribute.PAGE);
        if (currentPage == null) {
            currentPage = PagePath.HOME_PAGE;
        }
        return new Router(Router.RouteType.FORWARD, currentPage);
    }
}
