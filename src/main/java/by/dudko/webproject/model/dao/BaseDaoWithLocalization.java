package by.dudko.webproject.model.dao;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.entity.RootEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDaoWithLocalization <K, E extends RootEntity<K>> {
    List<E> findAllByLanguageId(int languageId) throws DaoException;

    Optional<E> findByIdAndLanguageId(K id, int languageId) throws DaoException;

    boolean deleteTranslation(K id, int languageId) throws DaoException;

    boolean deleteById(K id) throws DaoException;
}
