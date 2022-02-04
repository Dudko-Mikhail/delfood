package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.DishCategory;
import by.dudko.webproject.model.entity.Language;

import java.util.List;
import java.util.Optional;

public interface DishCategoryService {
    List<DishCategory> findAllCategoriesByLanguage(Language language) throws ServiceException;
    Optional<String> findCategoryNameByIdAndLanguage(int categoryId, Language language) throws ServiceException;
}
