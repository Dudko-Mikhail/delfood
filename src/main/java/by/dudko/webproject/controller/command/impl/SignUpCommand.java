package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.command.CommandType;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import by.dudko.webproject.util.encryption.Encryptor;
import by.dudko.webproject.util.encryption.impl.EncryptorImpl;
import by.dudko.webproject.util.mail.MailSender;
import by.dudko.webproject.util.mail.MailTemplates;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Optional;

import static by.dudko.webproject.controller.RequestParameter.LOGIN;
import static by.dudko.webproject.controller.RequestParameter.EMAIL;
import static by.dudko.webproject.controller.RequestParameter.PASSWORD;
import static by.dudko.webproject.controller.RequestParameter.REPEAT_PASSWORD;
import static by.dudko.webproject.controller.RequestParameter.PHONE_NUMBER;
import static by.dudko.webproject.controller.RequestParameter.FIRST_NAME;
import static by.dudko.webproject.controller.RequestParameter.LAST_NAME;

public class SignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HashMap<String, String> userData = new HashMap<>(); // TODO подумать над способом поставки данных для валидатора
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(REPEAT_PASSWORD, request.getParameter(REPEAT_PASSWORD));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        userData.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userData.put(LAST_NAME, request.getParameter(LAST_NAME));
        try {
            UserService userService = UserServiceImpl.getInstance();
            Optional<String> verificationCode = userService.signUp(userData);
            HttpSession session = request.getSession();
            String currentPage = (String) session.getAttribute(SessionAttribute.PAGE);
            if (currentPage == null) {
                currentPage = PagePath.HOME_PAGE;
            }
            if (verificationCode.isPresent()) { // TODO success registration action. Add mail logic and redirect to confirmation page
                String mail = MailTemplates.compileConfirmRegistrationTemplate(request.getRequestURL(),
                        userData.get(LOGIN), verificationCode.get());
                MailSender.getInstance().send(userData.get(EMAIL), "Authorization", mail);
            }
            request.setAttribute(RequestAttribute.SIGN_UP_RESULT, verificationCode.isPresent());
            return new Router(Router.RouteType.FORWARD, currentPage);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute SignUp command", e);
        }
    }
}
