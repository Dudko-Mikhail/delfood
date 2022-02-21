package by.dudko.webproject.controller.listener;

import by.dudko.webproject.model.pool.ConnectionPool;
import by.dudko.webproject.util.mail.MailSender;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        MailSender.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
