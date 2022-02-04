package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    boolean signUp(Map<String, String> userData) throws ServiceException;
    Optional<User> signIn(String login, String password) throws ServiceException;
}
