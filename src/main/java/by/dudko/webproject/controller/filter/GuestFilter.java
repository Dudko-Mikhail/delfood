package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.SessionAttributes;
import by.dudko.webproject.model.entity.Role;
import by.dudko.webproject.model.entity.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "guestFilter", urlPatterns = "/*")
public class GuestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Role role = (Role) session.getAttribute(SessionAttributes.ROLE);
        if (role == null) {
            session.setAttribute(SessionAttributes.ROLE, Role.GUEST);
        }
        chain.doFilter(request, response);
    }
}
