package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;


public class SignInCommand implements Command {
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String loginEmail = request.getParameter(RequestParameter.LOGIN_EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        Router router;
        try {
            Optional<User> optionalUser = userService.signIn(loginEmail, password);
            String currentPage = (String) request.getSession().getAttribute(SessionAttribute.PAGE);
            if (currentPage == null) {
                currentPage = PagePath.HOME_PAGE;
            }
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                switch (user.getStatus()) {
                    case UNCONFIRMED -> {
                        request.setAttribute(RequestAttribute.EMAIL_TO_VERIFY, user.getEmail());
                        router = new Router(Router.RouteType.FORWARD, currentPage);
                    }
                    case ACTIVE -> {
                        HttpSession session = request.getSession();
                        session.setAttribute(SessionAttribute.USER_LOGIN, user.getLogin());
                        session.setAttribute(SessionAttribute.ROLE, user.getRole());
                        router = switch (user.getRole()) {
                            case ADMIN -> new Router(Router.RouteType.REDIRECT, PagePath.ADMIN_PAGE);
                            case CLIENT -> new Router(Router.RouteType.REDIRECT, currentPage); // TODO client page
                            default -> new Router(Router.RouteType.REDIRECT, currentPage);
                        };
                    }
                    case BLOCKED -> {
                        request.setAttribute(RequestAttribute.SHOW_BLOCKED_ACCOUNT_MODAL, true);
                        router = new Router(Router.RouteType.FORWARD, currentPage);
                    }
                    default -> throw new CommandException("Unsupported user role " + user.getRole());
                }
            }
            else {
                request.setAttribute(RequestAttribute.INVALID_LOGIN_PASSWORD, true);
                request.setAttribute(RequestAttribute.SHOW_SIGN_IN_MODAL, true);
                router = new Router(Router.RouteType.FORWARD, currentPage);
            }
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute SignIn command", e);
        }
        return router;
    }
}
