package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.util.mail.EmailTemplate;

public interface EmailService {
    void sendEmailByTemplate(EmailTemplate template, String recipient) throws ServiceException;
}
