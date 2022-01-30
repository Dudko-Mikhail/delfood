package by.dudko.webproject.model.dao;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.entity.DishCategory;
import by.dudko.webproject.model.entity.Language;

import java.util.Optional;

public interface DishCategoryDao extends BaseDao<Integer, DishCategory> {
    Optional<DishCategory> findCategoryByIdAndLanguage(int categoryId, Language language) throws DaoException;
    Optional<DishCategory> findCategoryByIdAndLocaleName(int categoryId, String locale) throws DaoException;
    Optional<DishCategory> findCategoryByIdAndLocaleId(int categoryId, int localeId) throws DaoException;
    boolean addNewTranslation(int categoryId, Language language, String translation) throws DaoException;
}
