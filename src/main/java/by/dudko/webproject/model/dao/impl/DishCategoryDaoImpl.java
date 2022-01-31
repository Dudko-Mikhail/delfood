package by.dudko.webproject.model.dao.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.dao.DishCategoryDao;
import by.dudko.webproject.model.entity.DishCategory;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.mapper.impl.DishCategoryRowMapper;
import by.dudko.webproject.model.pool.ConnectionPool;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DishCategoryDaoImpl implements DishCategoryDao { // todo добавить update
    private static final String FIND_CATEGORY_BY_ID_AND_LOCALE_ID = """
            SELECT dc.category_id, image_url, category_name name
            FROM dish_categories dc
                 JOIN categories_translations AS ct on ct.category_id = dc.category_id
            WHERE dc.category_id = ? AND ct.language_id = ?
            """;
    private static final String FIND_CATEGORY_BY_ID_AND_LOCALE = """
            SELECT dc.category_id, image_url, category_name name
            FROM dish_categories dc
                 JOIN categories_translations AS ct on ct.category_id = dc.category_id
                 JOIN languages l on l.language_id = ct.language_id
            WHERE dc.category_id = ? AND l.name = ?
            """;
    private static final String DELETE_CATEGORY_BY_ID = """
            DELETE FROM dish_categories
            WHERE category_id = ?
            """;
    private static final String ADD_NEW_TRANSLATION = """
            INSERT INTO categories_translations (category_id, language_id, category_name)
            VALUES (?, ?, ?)
            """;
    private static final DishCategoryDaoImpl INSTANCE = new DishCategoryDaoImpl();
    private final ConnectionPool pool = ConnectionPool.getInstance();
    private final DishCategoryRowMapper categoryMapper = DishCategoryRowMapper.getInstance();

    public static DishCategoryDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DishCategory> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<DishCategory> findById(Integer id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(Integer id) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(DELETE_CATEGORY_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete category by id", e);
        }
    }

    @Override
    public boolean create(DishCategory dishCategory) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<DishCategory> findCategoryByIdAndLanguage(int categoryId, Language language) throws DaoException { // todo вынести в сервис
        return language.getId() != null
                ? findCategoryByIdAndLanguageId(categoryId, language.getId())
                : findCategoryByIdAndLanguageName(categoryId, language.getName());
    }

    @Override
    public Optional<DishCategory> findCategoryByIdAndLanguageName(int categoryId, String language) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_ID_AND_LOCALE)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setString(2, language);
            return categoryMapper.mapRow(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find dish category by id and language", e);
        }

    }

    @Override
    public Optional<DishCategory> findCategoryByIdAndLanguageId(int categoryId, int languageId) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_ID_AND_LOCALE_ID)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, languageId);
            return categoryMapper.mapRow(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw new DaoException("Failed to find dish category by id and language id", e);
        }
    }

    @Override
    public boolean addNewTranslation(int categoryId, Language language, String translation) throws DaoException { // TODO вынести в сервис
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(ADD_NEW_TRANSLATION)) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, language.getId());
            preparedStatement.setString(3, translation);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to add new translation to category", e);
        }
    }
}
