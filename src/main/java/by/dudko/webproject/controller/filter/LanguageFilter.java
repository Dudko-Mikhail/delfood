package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.SessionAttributes;
import by.dudko.webproject.util.Language;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

@WebFilter(filterName = "languageFilter", urlPatterns = "*.jsp")
public class LanguageFilter implements Filter {
    private static final String LOCALE_COOKIE_NAME = "localeCookie";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpSession session = httpRequest.getSession();
//        Language language = (Language) session.getAttribute(SessionAttributes.LANGUAGE);
//        if (language == null) {
//            Cookie localeCookie = Arrays.stream(httpRequest.getCookies())
//                    .filter(c -> c.getName().equals(LOCALE_COOKIE_NAME))
//                    .findFirst()
//                    .orElseGet(() -> new Cookie(LOCALE_COOKIE_NAME, Language.DEFAULT.getName()));
//            language = Language.parseLanguage(localeCookie.getValue());
//            session.setAttribute(SessionAttributes.LANGUAGE, language);
//        }
        chain.doFilter(request, response);
    }
}
