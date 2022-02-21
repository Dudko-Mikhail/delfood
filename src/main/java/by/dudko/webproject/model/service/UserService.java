package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<String> signUp(Map<String, String> userData) throws ServiceException;
    Optional<User> signIn(String login, String password) throws ServiceException;
    Optional<User> findUserByLogin(String login) throws ServiceException;
    boolean confirmRegistration(String login, String verificationCode) throws ServiceException;
//    boolean blockUser(int userId) throws ServiceException;
}
