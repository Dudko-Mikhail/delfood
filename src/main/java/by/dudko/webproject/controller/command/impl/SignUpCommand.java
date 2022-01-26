package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.SessionAttributes;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;

import static by.dudko.webproject.controller.RequestParameter.LOGIN;
import static by.dudko.webproject.controller.RequestParameter.EMAIL;
import static by.dudko.webproject.controller.RequestParameter.PASSWORD;
import static by.dudko.webproject.controller.RequestParameter.REPEAT_PASSWORD;
import static by.dudko.webproject.controller.RequestParameter.PHONE_NUMBER;
import static by.dudko.webproject.controller.RequestParameter.FIRST_NAME;
import static by.dudko.webproject.controller.RequestParameter.LAST_NAME;

public class SignUpCommand implements Command { // TODO
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HashMap<String, String> userData = new HashMap<>();
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(REPEAT_PASSWORD, request.getParameter(REPEAT_PASSWORD));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        userData.put(FIRST_NAME, request.getParameter(FIRST_NAME));
        userData.put(LAST_NAME, request.getParameter(LAST_NAME));
        Router router = new Router();
        try {
            boolean isRegistered = userService.signUp(userData);
            HttpSession session = request.getSession();
            if (isRegistered) { // TODO success registration action. Add mail logic and redirect to confirmation page
                router.setRouteType(Router.RouteType.FORWARD);
                router.setPagePath(PagePath.SIGN_IN_PAGE);
                session.setAttribute(SessionAttributes.LOGIN, request.getParameter(LOGIN));
            }
            else { // todo registration failed. Think about actions
                router.setRouteType(Router.RouteType.REDIRECT);
                router.setPagePath((String) session.getAttribute(SessionAttributes.PAGE));
            }
        } catch (ServiceException e) {
            throw new CommandException("Error during registration command", e);
        }
        return router;
    }
}