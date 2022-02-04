package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(SessionAttribute.ROLE, User.Role.GUEST);
        session.setAttribute(SessionAttribute.LOGIN, null);
        return new Router(Router.RouteType.REDIRECT, PagePath.HOME_PAGE);
    }
}
