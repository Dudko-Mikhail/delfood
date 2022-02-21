package by.dudko.webproject.controller.filter;

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
import java.util.List;
import java.util.Optional;

import static by.dudko.webproject.controller.CookieName.LOCALE_COOKIE;

@WebFilter(filterName = "languageFilter", urlPatterns = "/*")
public class LanguageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        if (session.getAttribute(SessionAttribute.LANGUAGE) == null) {
            LanguageProvider languageProvider = LanguageProvider.getInstance();
            Optional<Cookie> optionalCookie = CookieHelper.findCookieByName(LOCALE_COOKIE, httpRequest);
            Cookie localeCookie = optionalCookie.orElseGet(() -> {
                Cookie cookie = new Cookie(LOCALE_COOKIE, LanguageProvider.DEFAULT_LANGUAGE.getName());
                cookie.setMaxAge(CookieHelper.COOKIE_LIFE_TIME);
                ((HttpServletResponse) response).addCookie(cookie);
                return cookie;
            });
            Language language = languageProvider.parseLanguage(localeCookie.getValue());
            session.setAttribute(SessionAttribute.LANGUAGE, language);
        }

        if (session.getAttribute(SessionAttribute.LANGUAGES) == null) {
            List<String> languages = LanguageProvider.getInstance().getAvailableLanguageNames();
            session.setAttribute(SessionAttribute.LANGUAGES, languages);
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
        return !languageParameter.equals(sessionLanguage.getName());
    }

    private void updateLocaleCookie(HttpServletRequest request, HttpServletResponse response) {
        String newLanguage = request.getParameter(RequestParameter.LANGUAGE);
        Cookie localeCookie = CookieHelper.findCookieByName(LOCALE_COOKIE, request)
                .orElseGet(() -> new Cookie(LOCALE_COOKIE, newLanguage));
        localeCookie.setValue(newLanguage);
        localeCookie.setMaxAge(CookieHelper.COOKIE_LIFE_TIME);
        response.addCookie(localeCookie);
    }
}
