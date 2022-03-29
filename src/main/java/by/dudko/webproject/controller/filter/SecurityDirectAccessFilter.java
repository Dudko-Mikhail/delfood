package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.PagePath;
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

@WebFilter(filterName = "securityDirectAccessFilter", urlPatterns = "/jsp/*")
public class SecurityDirectAccessFilter implements Filter { // TODO implement later
    private enum AccessList {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String requestedUri = httpRequest.getRequestURI();
        String page = requestedUri.substring(requestedUri.indexOf("/jsp"));
        switch (page) {
            case PagePath.HOME_PAGE -> System.out.println("Home");
            case PagePath.DISH_PAGE -> System.out.println("Dish");
            default -> System.out.println("Zebra");
        }
        System.out.println(page);
        chain.doFilter(request, response);
    }
}
