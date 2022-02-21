package by.dudko.webproject.model.dao;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.entity.User;

import java.util.Optional;


public interface UserDao extends BaseDao<Long, User> {
    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    Optional<User> findUserByLoginOrEmail(String loginEmail) throws DaoException;

    boolean updateUserStatus(long userId, User.Status status) throws DaoException;
}
