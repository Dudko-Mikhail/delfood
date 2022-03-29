package by.dudko.webproject.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter(filterName = "securityCommandAccessFilter", urlPatterns = "/controller")
public class SecurityCommandAccessFilter implements Filter { // TODO скорее всего нужно будет добавить интерфейсу command метод EnumSet<User.Role> getAccessList
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
