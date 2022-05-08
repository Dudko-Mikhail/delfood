package by.dudko.webproject.util;

import by.dudko.webproject.model.entity.User;
import jakarta.servlet.ServletRequest;

public final class PathHelper {

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

    public static String getInitialPageAccordingUserRole(User.Role role) {
        return switch (role) {
            case ADMIN -> "/controller?command=go_to_admin_page";
            default -> "/controller?command=go_to_home_page";
        };
    }

    private PathHelper() {}
}
