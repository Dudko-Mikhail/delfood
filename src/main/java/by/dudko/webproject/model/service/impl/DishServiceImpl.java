package by.dudko.webproject.model.service.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.dao.DishDao;
import by.dudko.webproject.model.dao.impl.DishDaoImpl;
import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.service.DishService;

import java.util.List;
import java.util.Optional;

public class DishServiceImpl implements DishService {
    private static final DishServiceImpl INSTANCE = new DishServiceImpl();
    private final DishDao dishDao = DishDaoImpl.getInstance();

    public static DishServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Dish> findDishesByCategoryIdAndLanguage(int categoryId, Language language) throws ServiceException {
        try {
            return dishDao.findDishesByCategoryIdAndLanguageId(categoryId, language.getId());
        } catch (DaoException e) {
            throw new ServiceException("Failed to find dishes by category id and language id", e);
        }
    }

    @Override
    public Optional<Dish> findDishByIdAndLanguage(int dishId, Language language) throws ServiceException {
        try {
            return dishDao.findByIdAndLanguage(dishId, language);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find dish by id and language", e);
        }
    }

    private DishServiceImpl() {}
}
