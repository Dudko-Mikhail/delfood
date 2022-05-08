package by.dudko.webproject.controller.command.impl;

import by.dudko.webproject.controller.CookieName;
import by.dudko.webproject.controller.Router;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.exception.CommandException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import by.dudko.webproject.util.CookieHelper;
import by.dudko.webproject.util.PathHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class InitialCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Optional<Cookie> loginCookie = CookieHelper.findCookieByName(CookieName.LOGIN, request);
        Optional<Cookie> verificationCookie = CookieHelper.findCookieByName(CookieName.VERIFICATION, request);
        if (loginCookie.isPresent() && verificationCookie.isPresent()) {
            String login = loginCookie.get().getValue();
            String verificationCode = verificationCookie.get().getValue();
            if (verificationCode != null && login != null) {
                try {
                    Optional<User> optionalUser = userService.signInByVerificationCode(login, verificationCode);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        if (user.getStatus() == User.Status.ACTIVE) {
                            session.setAttribute(SessionAttribute.USER_LOGIN, login);
                            session.setAttribute(SessionAttribute.ROLE, user.getRole());
                        }
                    }
                } catch (ServiceException e) {
                    logger.error(e);
                }
            }
        }
        User.Role role = (User.Role) session.getAttribute(SessionAttribute.ROLE);
        String page = PathHelper.getInitialPageAccordingUserRole(role);
        return new Router(Router.RouteType.REDIRECT, page);
    }
}
