package by.dudko.webproject.model.dao.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.dao.DishDao;
import by.dudko.webproject.model.entity.Dish;
import by.dudko.webproject.model.mapper.impl.DishRowMapper;
import by.dudko.webproject.model.pool.ConnectionPool;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DishDaoImpl implements DishDao {
    private static final String FIND_ALL_DISHES_BY_LANGUAGE_ID = """
            SELECT d.dish_id, image_url, price, discount, weight, category_name category, description, dish_name
            FROM dishes d
            JOIN dish_translation dt ON d.dish_id = dt.dish_id
            JOIN categories_translations ct ON d.category_id = ct.category_id
                                            AND dt.language_id = ct.language_id
            WHERE dt.language_id = ?
            """;
    private static final String FIND_DISHES_BY_CATEGORY_ID_AND_LANGUAGE_ID = """
            SELECT d.dish_id, image_url, price, discount, weight, category_name category, description, dish_name
            FROM dishes d
            JOIN dish_translation dt ON d.dish_id = dt.dish_id
            JOIN categories_translations ct ON d.category_id = ct.category_id
                                            AND dt.language_id = ct.language_id
            WHERE ct.category_id = ? AND dt.language_id = ?
            """;
    private static final String FIND_DISH_BY_ID_AND_LANGUAGE_ID = """
            SELECT d.dish_id, image_url, price, discount, weight, category_name category, description, dish_name
            FROM dishes d
            JOIN dish_translation dt ON d.dish_id = dt.dish_id
            JOIN categories_translations ct ON d.category_id = ct.category_id
                                            AND dt.language_id = ct.language_id
            WHERE d.dish_id = ? AND dt.language_id = ?
            """;
    private static final String ADD_NEW_DISH_TRANSLATION = """
            INSERT INTO dish_translation (dish_id, language_id, dish_name, description)
            VALUES (?, ?, ?, ?)
            """;
    private static final String DELETE_DISH_BY_ID = """
            DELETE FROM dishes
            WHERE dish_id = ?
            """;
    private static final String DELETE_DISH_TRANSLATION = """
            DELETE FROM dish_translation
            WHERE dish_id = ? AND language_id = ?
            """;
    private static final DishDaoImpl INSTANCE = new DishDaoImpl();
    private final ConnectionPool pool = ConnectionPool.getInstance();
    private final DishRowMapper dishMapper = DishRowMapper.getInstance();

    public static DishDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Dish> findAllByLanguageId(int languageId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_DISHES_BY_LANGUAGE_ID)) {
            preparedStatement.setInt(1, languageId);
            return dishMapper.mapRows(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find all dishes by language id", e);
        }
    }

    @Override
    public List<Dish> findDishesByCategoryIdAndLanguageId(int categoryId, int languageId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_DISHES_BY_CATEGORY_ID_AND_LANGUAGE_ID)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, languageId);
            return dishMapper.mapRows(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find dishes by category id and language id", e);
        }
    }

    @Override
    public Optional<Dish> findByIdAndLanguageId(Integer dishId, int languageId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_DISH_BY_ID_AND_LANGUAGE_ID)) {
            preparedStatement.setInt(1, dishId);
            preparedStatement.setInt(2, languageId);
            return dishMapper.mapRow(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find dish id and language id", e);
        }
    }

    @Override
    public boolean addNewDishTranslation(int dishId, int languageId, String dishName, String dishDescription) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(ADD_NEW_DISH_TRANSLATION)) {
            preparedStatement.setInt(1, dishId);
            preparedStatement.setInt(2, languageId);
            preparedStatement.setString(3, dishName);
            preparedStatement.setString(4, dishDescription);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to add new translation to dish by dish id and language id", e);
        }
    }

    @Override
    public boolean deleteTranslation(Integer dishId, int languageId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(DELETE_DISH_TRANSLATION)) {
            preparedStatement.setInt(1, dishId);
            preparedStatement.setInt(2, languageId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete dish translation", e);
        }
    }

    @Override
    public boolean deleteById(Integer dishId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(DELETE_DISH_BY_ID)) {
            preparedStatement.setInt(1, dishId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete dish by id", e);
        }
    }

    private DishDaoImpl() {}
}
