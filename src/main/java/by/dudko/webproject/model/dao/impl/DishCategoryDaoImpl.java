package by.dudko.webproject.model.dao.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.dao.DishCategoryDao;
import by.dudko.webproject.model.entity.DishCategory;
import by.dudko.webproject.model.mapper.impl.DishCategoryRowMapper;
import by.dudko.webproject.model.pool.ConnectionPool;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DishCategoryDaoImpl implements DishCategoryDao {
    private static final String FIND_CATEGORY_BY_ID_AND_LANGUAGE_ID = """
            SELECT dc.category_id, image_url, category_name name
            FROM dish_categories dc
                 JOIN categories_translations AS ct on ct.category_id = dc.category_id
            WHERE dc.category_id = ? AND ct.language_id = ?
            """;
    private static final String FIND_CATEGORY_BY_ID_AND_LANGUAGE = """
            SELECT dc.category_id, image_url, category_name name
            FROM dish_categories dc
                 JOIN categories_translations AS ct on ct.category_id = dc.category_id
                 JOIN languages l on l.language_id = ct.language_id
            WHERE dc.category_id = ? AND l.name = ?
            """;
    private static final String FIND_ALL_CATEGORIES_BY_LANGUAGE = """
            SELECT dc.category_id, image_url, category_name name
            FROM dish_categories dc
                 JOIN categories_translations AS ct on ct.category_id = dc.category_id
                 JOIN languages l on l.language_id = ct.language_id
            WHERE l.name = ?
            """;
    private static final String FIND_ALL_CATEGORIES_BY_LANGUAGE_ID = """
            SELECT dc.category_id, image_url, category_name name
            FROM dish_categories dc
                 JOIN categories_translations AS ct on ct.category_id = dc.category_id
            WHERE ct.language_id = ?
            """;
    private static final String ADD_NEW_CATEGORY_TRANSLATION = """
            INSERT INTO categories_translations (category_id, language_id, category_name)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_CATEGORY_BY_ID = """
            DELETE FROM dish_categories
            WHERE category_id = ?
            """;
    private static final String DELETE_TRANSLATION_BY_CATEGORY_ID_AND_LANGUAGE_ID = """
            DELETE FROM categories_translations
            WHERE category_id = ? AND language_id = ?
            """;
    private static final DishCategoryDaoImpl INSTANCE = new DishCategoryDaoImpl();
    private final ConnectionPool pool = ConnectionPool.getInstance();
    private final DishCategoryRowMapper categoryMapper = DishCategoryRowMapper.getInstance();

    public static DishCategoryDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DishCategory> findAllByLanguageName(String languageName) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_CATEGORIES_BY_LANGUAGE)) {
            preparedStatement.setString(1, languageName);
            return categoryMapper.mapRows(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find all dish categories by language name", e);
        }
    }

    @Override
    public List<DishCategory> findAllByLanguageId(int languageId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_CATEGORIES_BY_LANGUAGE_ID)) {
            preparedStatement.setInt(1, languageId);
            return categoryMapper.mapRows(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find all dish categories by language id", e);
        }
    }

    @Override
    public Optional<DishCategory> findByIdAndLanguageName(Integer categoryId, String language) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_ID_AND_LANGUAGE)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setString(2, language);
            return categoryMapper.mapRow(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find dish category by id and language", e);
        }

    }

    @Override
    public Optional<DishCategory> findByIdAndLanguageId(Integer categoryId, int languageId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_ID_AND_LANGUAGE_ID)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, languageId);
            return categoryMapper.mapRow(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find dish category by id and language id", e);
        }
    }

    @Override
    public boolean addNewCategoryTranslation(int categoryId, int languageId, String translation) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(ADD_NEW_CATEGORY_TRANSLATION)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, languageId);
            preparedStatement.setString(3, translation);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to add new translation to category by category id and language id", e);
        }
    }

    @Override
    public boolean deleteById(Integer categoryId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(DELETE_CATEGORY_BY_ID)) {
            preparedStatement.setInt(1, categoryId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete category by id", e);
        }
    }

    @Override
    public boolean deleteTranslation(Integer categoryId, int languageId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(DELETE_TRANSLATION_BY_CATEGORY_ID_AND_LANGUAGE_ID)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, languageId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete category translation by category id and language id", e);
        }
    }
}
