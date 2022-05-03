package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.DishCategory;

import java.util.List;
import java.util.Optional;

public interface DishCategoryService {
    List<DishCategory> findAllCategoriesByLanguageId(int languageId) throws ServiceException;
    Optional<String> findCategoryNameByIdAndLanguageId(int categoryId, int languageId) throws ServiceException;
}
