package by.dudko.webproject.model.service.impl;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.service.EmailService;
import by.dudko.webproject.util.mail.EmailTemplate;
import by.dudko.webproject.util.mail.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LogManager.getLogger();
    private static final String CONFIG_FILE_PATH = "config/velocity.properties";
    private static final String ENCODING = "utf-8";
    private static final MailSender SENDER = MailSender.getInstance();
    private static final EmailServiceImpl INSTANCE = new EmailServiceImpl();
    private final VelocityEngine velocityEngine;

    public static EmailServiceImpl getInstance() {
        return INSTANCE;
    }

    private EmailServiceImpl() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_PATH));
            velocityEngine = new VelocityEngine(properties);
        } catch (IOException e) {
            logger.fatal("Failed to read configuration file from" + CONFIG_FILE_PATH, e);
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public void sendEmailByTemplate(EmailTemplate template, String recipient) throws ServiceException {
        if (!template.isConfigured()) {
            throw new ServiceException("Template is not configured");
        }
        String html = turnTemplateIntoString(template);
        sendEmail(recipient, template.getSubject(), html);
    }

    private String turnTemplateIntoString(EmailTemplate template) throws ServiceException {
        try (StringWriter writer = new StringWriter()) {
            velocityEngine.mergeTemplate(template.getTemplateName(), ENCODING, template.getVelocityContext(), writer);
            return writer.toString();
        } catch (IOException e) {
            throw new ServiceException("Unable to close writer");
        }
    }

    private void sendEmail(String recipient, String subject, String content) {
        Thread sender = configureSenderThread(recipient, subject, content);
        sender.start();
    }

    private Thread configureSenderThread(String recipient, String subject, String content) {
        return new Thread(() -> SENDER.send(recipient, subject, content));
    }
}



