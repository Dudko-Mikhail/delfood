package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
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


public class SignInCommand implements Command { // todo add messages
    @Override
    public Router execute(HttpServletRequest request) throws CommandException { // TODO refactor
        String loginEmail = request.getParameter(RequestParameter.LOGIN_EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        Router router;
        try {
            UserService userService = UserServiceImpl.getInstance();
            Optional<User> optionalUser = userService.signIn(loginEmail, password);
            if (optionalUser.isPresent()) { // FIXME изменить логику с добавлением страниц (Admin page, client page)
                User user = optionalUser.get();
                switch (user.getStatus()) {
                    case UNCONFIRMED -> { // todo add unconfirmed user action

                        router = new Router(Router.RouteType.REDIRECT, PagePath.SIGN_IN_PAGE);
                    }
                    case ACTIVE -> {
                        HttpSession session = request.getSession();
                        session.setAttribute(SessionAttribute.LOGIN, user.getLogin());
                        session.setAttribute(SessionAttribute.ROLE, user.getRole());
                        router = switch (user.getRole()) {
                            case ADMIN -> new Router(Router.RouteType.FORWARD, PagePath.ADMIN_PAGE);
                            case CLIENT -> new Router(Router.RouteType.FORWARD, PagePath.HOME_PAGE); // TODO client page
                            default -> new Router(Router.RouteType.REDIRECT, PagePath.SIGN_IN_PAGE);
                        };
                    }
                    case BLOCKED -> { // TODO add blocked action
                        router = new Router(Router.RouteType.REDIRECT, PagePath.SIGN_IN_PAGE);
                    }
                    default -> throw new CommandException("Unsupported user role " + user.getRole());
                }
            }
            else {
                router = new Router(Router.RouteType.REDIRECT, PagePath.SIGN_UP_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute SignIn command", e);
        }
        return router;
    }
}
