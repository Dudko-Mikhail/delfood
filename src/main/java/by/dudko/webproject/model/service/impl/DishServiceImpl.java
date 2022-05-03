package by.dudko.webproject.model.service.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.dao.DishDao;
import by.dudko.webproject.model.dao.impl.DishDaoImpl;
import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.service.DishService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DishServiceImpl implements DishService {
    private static final DishServiceImpl INSTANCE = new DishServiceImpl();
    private final DishDao dishDao = DishDaoImpl.getInstance();

    public static DishServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Dish> findDishesByCategoryIdAndLanguageId(int categoryId, int languageId) throws ServiceException {
        try {
            return dishDao.findDishesByCategoryIdAndLanguageId(categoryId, languageId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find dishes by category id and language id", e);
        }
    }

    @Override
    public Optional<Dish> findDishByIdAndLanguageId(int dishId, int languageId) throws ServiceException {
        try {
            return dishDao.findByIdAndLanguageId(dishId, languageId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find dish by id and language id", e);
        }
    }

    @Override
    public List<Dish> findDishedByIdSetAndLanguageId(Set<Integer> idSet, int languageId) throws ServiceException {
        try {
            List<Dish> dishes = dishDao.findAllByLanguageId(languageId);
            return dishes.stream().filter(d -> idSet.contains(d.getId()))
                    .toList();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find dishes by id set and language id");
        }
    }

    private DishServiceImpl() {}
}
