package by.dudko.webproject.util;

import jakarta.servlet.ServletRequest;

public final class PathUtils {
    public static String addContextPath(ServletRequest request, String page) {
        if (page.contains("http")) {
            return page;
        }
        String contextPath = request.getServletContext().getContextPath();
        String pagePath = page;
        if (contextPath.endsWith("/")) {
            pagePath = page.replaceFirst("/", "");
        }
        return contextPath + pagePath;
    }

    private PathUtils() {}
}
