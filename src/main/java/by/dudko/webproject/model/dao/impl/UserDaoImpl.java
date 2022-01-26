package by.dudko.webproject.model.dao.impl;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.dao.UserDao;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.mapper.impl.UserRowMapper;
import by.dudko.webproject.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final ConnectionPool pool = ConnectionPool.getInstance();
    private static final UserRowMapper userMapper = UserRowMapper.getInstance();
    private static final String SELECT_ALL_USERS = """
            SELECT user_id, roles.name role, status, email, login, password, phone_number, first_name, last_name
            FROM users JOIN user_roles roles
                       on roles.role_id = users.role_id
            """;
    private static final String FIND_USER_BY_ID = """
            SELECT user_id, roles.name role, status, email, login, password, phone_number, first_name, last_name
            FROM users JOIN user_roles roles
                       on roles.role_id = users.role_id
            WHERE users.user_id = ?
            """;
    private static final String FIND_USER_BY_LOGIN = """
            SELECT user_id, roles.name role, status, email, login, password, phone_number, first_name, last_name
            FROM users JOIN user_roles roles
                       on roles.role_id = users.role_id
            WHERE login = ?
            """;
    private static final String FIND_USER_BY_EMAIL = """
            SELECT user_id, roles.name role, status, email, login, password, phone_number, first_name, last_name
            FROM users JOIN user_roles roles
                       on roles.role_id = users.role_id
            WHERE email = ?
            """;
    private static final String DELETE_USER_BY_ID = """
            DELETE FROM users
            WHERE user_id = ?
            """;
    private static final String CREATE_USER = """
            INSERT INTO users (role_id, status, email, login, password, phone_number, first_name, last_name)
            values (?, ?, ?, ?, ?, ?, ?, ?)
            """;
    private static final UserDaoImpl INSTANCE = new UserDaoImpl();

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<User> findAll() throws DaoException {
        ArrayList<User> users = new ArrayList<>();
        try (var connection = pool.takeConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(SELECT_ALL_USERS)) {
            users.addAll(userMapper.mapRows(resultSet));
        } catch (SQLException e) {
            throw new DaoException("Failed to find all users", e);
        }
        return users;
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (var resultSet = preparedStatement.executeQuery()) {
                return userMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user by id", e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (var resultSet = preparedStatement.executeQuery()) {
                return userMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user by login", e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (var resultSet = preparedStatement.executeQuery()) {
                return userMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to find user by email", e);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID)) {
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to delete user by id", e);
        }
    }

    @Override
    public boolean create(User user) throws DaoException {
        try (var connection = pool.takeConnection();
             var preparedStatement = connection.prepareStatement(CREATE_USER)) {
            preparedStatement.setInt(1, user.getRole().ordinal() + 1);
            preparedStatement.setString(2, user.getStatus().toString());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setString(7, user.getFirstName());
            preparedStatement.setString(8, user.getLastName());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Failed to create user", e);
        }
    }

    private UserDaoImpl() {
    }
}
