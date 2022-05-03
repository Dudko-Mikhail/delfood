package by.dudko.webproject.util;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.dao.impl.LanguageDao;
import by.dudko.webproject.model.entity.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LanguageProvider { // TODO Ask some questions;
    public static final Language DEFAULT_LANGUAGE = new Language("en_US");
    private static final Logger logger = LogManager.getLogger();
    private static final LanguageProvider INSTANCE;
    private static final List<Language> languages;

    static {
        INSTANCE = new LanguageProvider();
        LanguageDao languageDao = LanguageDao.getInstance();
        try {
            languages = languageDao.findAll();
            languages.add(DEFAULT_LANGUAGE); // todo надо ли?
        } catch (DaoException e) {
            logger.fatal("Failed to read languages from db");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static LanguageProvider getInstance() {
        return INSTANCE;
    }

    private LanguageProvider() {}

    public boolean isValidLanguage(String language) { // TODO maybe remove from here
        return languages.stream()
                .anyMatch(l -> l.getName().equals(language));
    }


    public Language parseLanguage(String languageName) {
        int i = 0;
        while (i < languages.size()) {
            Language language = languages.get(i);
            if (language.getName().equals(languageName)) {
                return language;
            }
            i++;
        }
        return DEFAULT_LANGUAGE;
    }

    public List<String> getAvailableLanguageNames() {
        return languages.stream()
                .map(Language::getName)
                .distinct()
                .toList();
    }

}
