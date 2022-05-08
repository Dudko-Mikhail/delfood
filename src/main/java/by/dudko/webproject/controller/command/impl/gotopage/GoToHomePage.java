package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.command.RoutingCommand;
import by.dudko.webproject.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToHomePage implements RoutingCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(Router.RouteType.FORWARD, PagePath.HOME_PAGE);
    }
}
