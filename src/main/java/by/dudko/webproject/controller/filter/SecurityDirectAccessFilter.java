package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.SessionAttribute;
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

@WebFilter(filterName = "securityDirectAccessFilter", urlPatterns = "/jsp/*")
public class SecurityDirectAccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String currentPage = (String) session.getAttribute(SessionAttribute.PAGE);
        httpResponse.sendRedirect(PathHelper.addContextPath(request, currentPage));
        chain.doFilter(request, response);
    }
}
