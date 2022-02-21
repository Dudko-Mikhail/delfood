package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.Language;

import java.util.List;

public interface LanguageService {
    List<Language> findAllLanguages() throws ServiceException;
}
