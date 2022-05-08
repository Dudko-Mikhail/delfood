package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.CommandProvider;
import by.dudko.webproject.controller.command.CommandType;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.util.PathHelper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@WebFilter(filterName = "securityCommandAccessFilter", urlPatterns = "/controller")
public class SecurityCommandAccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        if (commandName != null) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            HttpSession session = httpRequest.getSession();
            Set<User.Role> accessList = getAccessList(httpRequest);
            User.Role role = (User.Role) session.getAttribute(SessionAttribute.ROLE);
            if (!accessList.contains(role)) {
                httpResponse.sendRedirect(PathHelper.addContextPath(request, PagePath.INDEX_PAGE));
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private Set<User.Role> getAccessList(HttpServletRequest request) {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        Optional<CommandType> commandType = CommandProvider.parseCommand(commandName);
        return commandType.isEmpty() ? Collections.emptySet() : commandType.get().getRoleAccessList();
    }
}
