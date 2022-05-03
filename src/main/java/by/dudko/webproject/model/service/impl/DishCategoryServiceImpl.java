package by.dudko.webproject.model.service.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.dao.DishCategoryDao;
import by.dudko.webproject.model.dao.impl.DishCategoryDaoImpl;
import by.dudko.webproject.model.entity.DishCategory;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.service.DishCategoryService;

import java.util.List;
import java.util.Optional;

public class DishCategoryServiceImpl implements DishCategoryService {
    private static final DishCategoryServiceImpl INSTANCE = new DishCategoryServiceImpl();
    private final DishCategoryDao categoryDao = DishCategoryDaoImpl.getInstance();

    public static DishCategoryServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DishCategory> findAllCategoriesByLanguageId(int languageId) throws ServiceException {
        try {
            return categoryDao.findAllByLanguageId(languageId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find categories by language", e);
        }
    }

    @Override
    public Optional<String> findCategoryNameByIdAndLanguageId(int categoryId, int languageId) throws ServiceException {
        try {
            Optional<DishCategory> category = categoryDao.findByIdAndLanguageId(categoryId, languageId);
            return category.map(DishCategory::getName);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find category name by id and language");
        }
    }

    private DishCategoryServiceImpl() {}
}
