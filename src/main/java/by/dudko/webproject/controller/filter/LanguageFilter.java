package by.dudko.webproject.controller.filter;

import by.dudko.webproject.controller.CookieName;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.util.CookieHelper;
import by.dudko.webproject.util.LanguageProvider;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static by.dudko.webproject.controller.CookieName.LOCALE_COOKIE;

@WebFilter(filterName = "languageFilter", urlPatterns = "/*")
public class LanguageFilter implements Filter {
    private static final int ONE_WEEK = 60 * 60 * 24 * 7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        if (session.getAttribute(SessionAttribute.LANGUAGE) == null) {
            LanguageProvider languageProvider = LanguageProvider.getInstance();
            Optional<Cookie> optionalCookie = CookieHelper.findCookieByName(LOCALE_COOKIE, httpRequest);
            Cookie localeCookie = optionalCookie.orElseGet(() -> {
                Cookie cookie = new Cookie(LOCALE_COOKIE, LanguageProvider.DEFAULT_LANGUAGE.getName());
                cookie.setMaxAge(ONE_WEEK);
                ((HttpServletResponse) response).addCookie(cookie);
                return cookie;
            });
            Language language = languageProvider.parseLanguage(localeCookie.getValue());
            session.setAttribute(SessionAttribute.LANGUAGE, language); // fixme ask question
        }

        if (isLanguageChanged(httpRequest)) {
            updateLocaleCookie(httpRequest, (HttpServletResponse) response);
        }

        chain.doFilter(request, response);
    }

    private boolean isLanguageChanged(HttpServletRequest request) {
        String languageParameter = request.getParameter(RequestParameter.LANGUAGE);
        if (languageParameter == null) {
            return false;
        }
        HttpSession session = request.getSession();
        Language sessionLanguage = (Language) session.getAttribute(SessionAttribute.LANGUAGE);
        return languageParameter.equals(sessionLanguage.getName());
    }

    private void updateLocaleCookie(HttpServletRequest request, HttpServletResponse response) {
        String newLanguage = request.getParameter(RequestParameter.LANGUAGE);
        Cookie localeCookie = CookieHelper.findCookieByName(LOCALE_COOKIE, request)
                .orElseGet(() -> new Cookie(LOCALE_COOKIE, newLanguage));
        localeCookie.setValue(newLanguage);
        localeCookie.setMaxAge(ONE_WEEK);
        response.addCookie(localeCookie);
    }
}
