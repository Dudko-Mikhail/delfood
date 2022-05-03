package by.dudko.webproject.util.i18n;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.dao.impl.LanguageDaoImpl;
import by.dudko.webproject.model.entity.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class LanguageProvider { // TODO Ask some questions;
    private static final Logger logger = LogManager.getLogger();
    private static final LanguageProvider INSTANCE;
    private static final List<Language> languages;
    public static final Language DEFAULT_LANGUAGE;
    private static final String DEFAULT_LANGUAGE_NAME = "en_US";

    static {
        INSTANCE = new LanguageProvider();
        LanguageDaoImpl languageDao = LanguageDaoImpl.getInstance();
        try {
            languages = languageDao.findAll();
            DEFAULT_LANGUAGE = parseDefaultLanguage();
        } catch (DaoException e) {
            logger.fatal("Failed to load languages from db");
            throw new ExceptionInInitializerError(e);
        }
    }

    private static Language parseDefaultLanguage() {
        Optional<Language> optionalLanguage = languages.stream()
                .filter(l -> l.getName().equals(DEFAULT_LANGUAGE_NAME))
                .findAny();
        return optionalLanguage.orElseThrow(() -> new ExceptionInInitializerError("Failed to find" +
                "default language among available languages. Default language name is " + DEFAULT_LANGUAGE_NAME));
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
