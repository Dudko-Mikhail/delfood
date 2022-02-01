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
    private final UserService userService = UserServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String loginEmail = request.getParameter(RequestParameter.LOGIN_EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        Router router = new Router();
        try {
            Optional<User> optionalUser = userService.signIn(loginEmail, password);
            if (optionalUser.isPresent()) { // FIXME изменить логику с добавлением страниц (Admin page, client page)
                User user = optionalUser.get();
                switch (user.getStatus()) {
                    case UNCONFIRMED -> {
                        // todo add unconfirmed user action
                    }
                    case ACTIVE -> {
                        HttpSession session = request.getSession();
                        session.setAttribute(SessionAttribute.LOGIN, user.getLogin());
                        session.setAttribute(SessionAttribute.ROLE, user.getRole());
                        switch (user.getRole()) {
                            case ADMIN -> router.setPagePath(PagePath.ADMIN_PAGE);
                            case CLIENT -> router.setPagePath(PagePath.HOME_PAGE); // TODO client page
                        }
                        router.setRouteType(Router.RouteType.FORWARD);

                    }
                    case BLOCKED -> {
                        // TODO add blocked user action
                    }
                    default -> throw new CommandException("Unsupported user role " + user.getRole());
                }
            }
            else {
                router.setPagePath(PagePath.SIGN_IN_PAGE);
                router.setRouteType(Router.RouteType.REDIRECT);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error during sign in command", e);
        }
        return router;
    }
}
