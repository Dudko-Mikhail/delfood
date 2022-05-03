package by.dudko.webproject.controller.listener;

import by.dudko.webproject.controller.SessionAttribute;
import by.dudko.webproject.model.entity.OrderManager;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        OrderManager manager = (OrderManager) session.getAttribute(SessionAttribute.ORDER_MANAGER);
        if (manager == null) {
            session.setAttribute(SessionAttribute.ORDER_MANAGER, new OrderManager());
        }
    }
}
