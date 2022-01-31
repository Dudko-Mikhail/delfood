package by.dudko.webproject.model.dao.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.dao.BaseDao;
import by.dudko.webproject.model.entity.Language;
import by.dudko.webproject.model.mapper.impl.LanguageRowMapper;
import by.dudko.webproject.model.pool.ConnectionPool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LanguageDao implements BaseDao<Integer, Language> {
    private static final String FIND_ALL_LANGUAGES = """
            SELECT language_id, name
            FROM languages
            """;
    private static final String FIND_LANGUAGE_BY_ID = """
            SELECT language_id, name
            FROM languages
            WHERE language_id = ?
            """;
    private static final String DELETE_LANGUAGE_BY_ID = """
            DELETE FROM languages
            WHERE language_id = ?
            """;
    private static final String CREATE_LANGUAGE = """
            INSERT INTO languages (name)
            VALUES (?)
            """;
    private static final LanguageDao INSTANCE = new LanguageDao();
    private final LanguageRowMapper languageMapper = LanguageRowMapper.getInstance();
    private final ConnectionPool pool = ConnectionPool.getInstance();

    public static LanguageDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Language> findAll() throws DaoException {
        ArrayList<Language> languages = new ArrayList<>();
        try (var connection = pool.takeConnection();
             var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery(FIND_ALL_LANGUAGES)) {
                languages.addAll(languageMapper.mapRows(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find all languages", e);
        }
        return languages;
    }

    @Override
    public Optional<Language> findById(Integer id) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_LANGUAGE_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                return languageMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find language by id", e);
        }
    }

    @Override
    public boolean deleteById(Integer id) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(DELETE_LANGUAGE_BY_ID)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to find language by id", e);
        }
    }

    @Override
    public boolean create(Language language) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(CREATE_LANGUAGE)) {
            preparedStatement.setString(1, language.getName());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create language", e);
        }
    }

    private LanguageDao() {}
}
