package by.dudko.webproject.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class CookieHelper {
    public static final int COOKIE_LIFE_TIME = (int) TimeUnit.DAYS.toSeconds(7);

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
