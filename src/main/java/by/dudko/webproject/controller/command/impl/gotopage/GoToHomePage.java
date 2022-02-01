package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToHomePage implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(Router.RouteType.REDIRECT, PagePath.HOME_PAGE);
    }
}
