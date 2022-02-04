package by.dudko.webproject.model.dao;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.entity.DishCategory;

public interface DishCategoryDao extends BaseDaoWithLocalization<Integer, DishCategory> {
    boolean addNewCategoryTranslation(int categoryId, int localeId, String translation) throws DaoException;
}
