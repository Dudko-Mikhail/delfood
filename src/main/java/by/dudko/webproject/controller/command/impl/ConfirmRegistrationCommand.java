package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class ConfirmRegistrationCommand implements Command { // TODO refactor according userStatus
    private static final UserService userService = UserServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String email = request.getParameter(RequestParameter.EMAIL);
        String verificationCode = request.getParameter(RequestParameter.VERIFICATION_CODE);
        try {
            boolean verificationResult = userService.confirmRegistration(email, verificationCode); // TODO add blocked user actions and add actions in case of confirmation fail
            request.setAttribute(RequestAttribute.VERIFICATION_RESULT, verificationResult);
            return new Router(Router.RouteType.FORWARD, PagePath.HOME_PAGE);
        } catch (ServiceException e) {
            throw new CommandException("Failed to execute confirmRegistration command", e);
        }
    }
}
