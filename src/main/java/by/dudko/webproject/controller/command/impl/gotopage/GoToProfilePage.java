package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.command.RoutingCommand;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class GoToProfilePage implements RoutingCommand {
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(SessionAttribute.USER_LOGIN);
        try {
            Optional<User> optionalUser = userService.findUserByLogin(login);
            if (optionalUser.isEmpty()) {
                return new Router(HttpServletResponse.SC_NOT_FOUND);
            }
            request.setAttribute(RequestAttribute.USER, optionalUser.get());
            return new Router(Router.RouteType.FORWARD, PagePath.PROFILE_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute GoToProfilePage command");
        }
    }
}
