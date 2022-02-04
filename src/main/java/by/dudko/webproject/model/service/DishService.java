package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.entity.Language;

import java.util.List;
import java.util.Optional;

public interface DishService {
    List<Dish> findDishesByCategoryIdAndLanguage(int categoryId, Language language) throws ServiceException;
    Optional<Dish> findDishByIdAndLanguage(int dishId, Language language) throws ServiceException;
}
