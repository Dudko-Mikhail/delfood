package by.dudko.webproject.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter(filterName = "encodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter { // TODO Лучше константа или initParam?
    private static final String ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestEncoding = request.getCharacterEncoding();
        if (requestEncoding == null || !requestEncoding.equalsIgnoreCase(ENCODING)) {
            request.setCharacterEncoding(ENCODING);
            response.setCharacterEncoding(ENCODING);
        }
        chain.doFilter(request, response);
    }
}
