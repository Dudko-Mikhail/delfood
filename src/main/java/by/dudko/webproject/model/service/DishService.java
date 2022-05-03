package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.Dish;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DishService {
    List<Dish> findDishesByCategoryIdAndLanguageId(int categoryId, int languageId) throws ServiceException;

    Optional<Dish> findDishByIdAndLanguageId(int dishId, int languageId) throws ServiceException;

    List<Dish> findDishedByIdSetAndLanguageId(Set<Integer> idSet, int languageId) throws ServiceException;
}
