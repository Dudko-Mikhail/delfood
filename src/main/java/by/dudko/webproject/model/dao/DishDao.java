package by.dudko.webproject.model.dao;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.entity.Dish;

import java.util.List;

public interface DishDao extends BaseDaoWithLocalization<Integer, Dish> {
    boolean addNewDishTranslation(int dishId, int languageId, String dishName, String dishDescription) throws DaoException;
    List<Dish> findDishesByCategoryIdAndLanguageId(int categoryId, int languageId) throws DaoException;
}
