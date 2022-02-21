package by.dudko.webproject.util.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private static final String MAIL_PROPERTY_PATH = "config/mail.properties";
    private static final String MAIL_USER_NAME = "mail.user.name";
    private static final String MAIL_USER_PASSWORD = "mail.user.password";
    private final Properties mailProperties;
    private static MailSender instance;

    private MailSender() {
        try {
            mailProperties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(MAIL_PROPERTY_PATH);
            mailProperties.load(inputStream);
        } catch (IOException e) {
            logger.fatal("Mail properties read error", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static MailSender getInstance() {
        if (instance == null) {
            instance = new MailSender();
        }
        return instance;
    }

    public void send(String mailRecipient, String mailSubject, String mailContent) {
        try {
            MimeMessage message = new MimeMessage(createSession());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailRecipient));
            message.setSubject(mailSubject);
            message.setContent(mailContent, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error(String.format("Failed to send the message to %s", mailRecipient), e);
        }
    }

    private Session createSession() {
        return Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String senderMail = mailProperties.getProperty(MAIL_USER_NAME);
                String senderPassword = mailProperties.getProperty(MAIL_USER_PASSWORD);
                return new PasswordAuthentication(senderMail, senderPassword);
            }
        });
    }
}
