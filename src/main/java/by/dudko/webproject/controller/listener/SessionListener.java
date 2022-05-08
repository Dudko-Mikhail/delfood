package by.dudko.webproject.controller.listener;

import by.dudko.webproject.controller.PagePath;
import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.model.entity.OrderManager;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.util.i18n.LanguageProvider;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.List;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) { // Вызывается дважды
        HttpSession session = se.getSession();
        OrderManager manager = (OrderManager) session.getAttribute(SessionAttribute.ORDER_MANAGER);
        if (manager == null) {
            session.setAttribute(SessionAttribute.ORDER_MANAGER, new OrderManager());
        }
        LanguageProvider provider = LanguageProvider.getInstance();
        List<String> languages = (List<String>) session.getAttribute(SessionAttribute.LANGUAGES);
        if (languages == null) {
            session.setAttribute(SessionAttribute.LANGUAGES, provider.getAvailableLanguageNames());
        }
        User.Role role = (User.Role) session.getAttribute(SessionAttribute.ROLE);
        if (role == null) {
            session.setAttribute(SessionAttribute.ROLE, User.Role.GUEST);
        }
        String page = (String) session.getAttribute(SessionAttribute.PAGE);
        if (page == null) {
            session.setAttribute(SessionAttribute.PAGE, PagePath.INDEX_PAGE);
        }
    }
}
