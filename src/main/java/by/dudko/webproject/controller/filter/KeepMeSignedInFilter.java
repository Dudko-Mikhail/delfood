package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.CookieName;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.command.CommandType;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.model.service.impl.UserServiceImpl;
import by.dudko.webproject.util.CookieHelper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "keepMeSignedIn", urlPatterns = "/controller")
public class KeepMeSignedInFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (isSignInCommand(request)) {
            try {
                serveSignInCommand(httpRequest, httpResponse);
            } catch (ServiceException e) {
                logger.error(e);
            }
        }
        if (isSignOutCommand(request)) {
            serveSignOutCommand(httpRequest, httpResponse);
        }
        chain.doFilter(request, response);
    }

    private boolean isSignInCommand(ServletRequest request) {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        String singInCommand = CommandType.SIGN_IN.name();
        return singInCommand.equalsIgnoreCase(commandName);
    }

    private void serveSignInCommand(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String loginEmail = request.getParameter(RequestParameter.LOGIN_EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String keepMeSignedIn = request.getParameter(RequestParameter.KEEP_ME_SIGNED_IN);
        if (keepMeSignedIn != null) {
            Optional<User> optionalUser = userService.signIn(loginEmail, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getStatus() == User.Status.ACTIVE) {
                    String verificationCode = userService.generateUserVerificationCodeByEmail(user.getEmail());
                    Cookie loginCookie = new Cookie(CookieName.LOGIN, user.getLogin());
                    Cookie verification = new Cookie(CookieName.VERIFICATION, verificationCode);
                    response.addCookie(loginCookie);
                    response.addCookie(verification);
                }
            }
        }
    }

    private boolean isSignOutCommand(ServletRequest request) {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        String singOutCommand = CommandType.SIGN_OUT.name();
        return singOutCommand.equalsIgnoreCase(commandName);
    }

    private void serveSignOutCommand(HttpServletRequest request, HttpServletResponse response) {
        CookieHelper.removeCookie(request, response, CookieName.LOGIN);
        CookieHelper.removeCookie(request, response, CookieName.VERIFICATION);
    }
}
