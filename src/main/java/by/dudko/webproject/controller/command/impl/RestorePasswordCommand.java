package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class RestorePasswordCommand implements Command { // TODO implement command
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter(RequestParameter.EMAIL);
        String currentPage = (String) request.getSession().getAttribute(SessionAttribute.PAGE);
        if (currentPage == null) {
            currentPage = PagePath.HOME_PAGE;
        }
        return new Router(Router.RouteType.FORWARD, currentPage);
    }
}
