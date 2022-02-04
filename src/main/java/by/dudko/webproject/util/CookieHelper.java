package by.dudko.webproject.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public final class CookieHelper {
    public static Optional<Cookie> findCookieByName(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        Cookie cookie = null;
        int i = 0;
        while (i < cookies.length){
            if (cookies[i].getName().equals(cookieName)) {
                cookie = cookies[i];
                break;
            }
            i++;
        }
        return Optional.ofNullable(cookie);
    }

    private CookieHelper() {}
}
