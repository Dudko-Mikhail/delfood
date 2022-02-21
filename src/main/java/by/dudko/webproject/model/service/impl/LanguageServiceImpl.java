package by.dudko.webproject.model.service.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.dao.impl.LanguageDao;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.service.LanguageService;

import java.util.List;

public class LanguageServiceImpl implements LanguageService {
    private static final LanguageServiceImpl INSTANCE = new LanguageServiceImpl();
    private final LanguageDao languageDao = LanguageDao.getInstance();

    public static LanguageServiceImpl getInstance() {
        return INSTANCE;
    }

    private LanguageServiceImpl() {}

    @Override
    public List<Language> findAllLanguages() throws ServiceException {
        try {
            return languageDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find all languages", e);
        }
    }
}
