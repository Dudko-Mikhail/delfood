package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.controller.command.Command;
import by.dudko.webproject.controller.command.CommandProvider;
import by.dudko.webproject.controller.command.CommandType;
import by.dudko.webproject.controller.command.RoutingCommand;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "currentPageFilter", urlPatterns = "/controller",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class CurrentPageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String commandName = request.getParameter(RequestParameter.COMMAND);
        if (isRoutingCommand(commandName)) {
            String pageRequest = buildRequestString(httpRequest);
            session.setAttribute(SessionAttribute.PAGE, pageRequest);
        }
        chain.doFilter(request, response);
    }

    private boolean isRoutingCommand(String commandName) {
        Optional<CommandType> commandTypeOptional = CommandProvider.parseCommand(commandName);
        if (commandTypeOptional.isPresent()) {
            CommandType type = commandTypeOptional.get();
            Command command = type.getCommand();
            return command instanceof RoutingCommand;

        }
        return false;
    }

    private String buildRequestString(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return "/controller?" + queryString;
    }
}
