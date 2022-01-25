package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.SessionAttributes;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "currentPageFilter", urlPatterns = "*.jsp")
public class CurrentPageFilter implements Filter {
    private static final String INDEX_PAGE = "index.jsp";
    private static final String PAGES_ROOT = "/jsp";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        int pageStartIndex = uri.indexOf(PAGES_ROOT);
        String currentPage = pageStartIndex != -1 ? uri.substring(pageStartIndex) : INDEX_PAGE;

        HttpSession session = httpRequest.getSession();
        session.setAttribute(SessionAttributes.PAGE, currentPage);
        chain.doFilter(request, response);
    }
}
