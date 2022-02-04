package by.dudko.webproject.model.dao;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.entity.RootEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BaseDaoWithLocalization <K, E extends RootEntity<K>> {
    List<E> findAllByLanguageName(String languageName) throws DaoException;

    List<E> findAllByLanguageId(int languageId) throws DaoException;

    default List<E> findAllByLanguage(Language language) throws DaoException {
        if (language.getId() != null) {
            return findAllByLanguageId(language.getId());
        }
        else if (language.getName() != null) {
            return findAllByLanguageName(language.getName());
        }
        else {
            return new ArrayList<>();
        }
    }

    Optional<E> findByIdAndLanguageId(K id, int languageId) throws DaoException;

    Optional<E> findByIdAndLanguageName(K id, String languageName) throws DaoException;

    default Optional<E> findByIdAndLanguage(K id, Language language) throws DaoException {
        if (language.getId() != null) {
            return findByIdAndLanguageId(id, language.getId());
        }
        else if (language.getName() != null) {
            return findByIdAndLanguageName(id, language.getName());
        }
        else {
            return Optional.empty();
        }
    }

    boolean deleteTranslation(K id, int languageId) throws DaoException;

    boolean deleteById(K id) throws DaoException;


}
