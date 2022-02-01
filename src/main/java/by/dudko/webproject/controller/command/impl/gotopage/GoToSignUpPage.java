package by.dudko.webproject.controller.command.impl.gotopage;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class GoToSignUpPage implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(Router.RouteType.REDIRECT, PagePath.SIGN_UP_PAGE);
    }
}
