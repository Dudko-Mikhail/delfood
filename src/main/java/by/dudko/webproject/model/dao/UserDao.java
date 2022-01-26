package by.dudko.webproject.model.dao;

import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> { // TODO fill as you need
    Optional<User> findUserByLogin(String login) throws DaoException;
    Optional<User> findUserByEmail(String email) throws DaoException;
}
