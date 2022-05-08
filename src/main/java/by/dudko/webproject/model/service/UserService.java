package by.dudko.webproject.model.service;

import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.entity.User;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Map<String, String> signUp(Map<String, String> userData) throws ServiceException;

    Optional<User> signIn(String loginEmail, String password) throws ServiceException;

    Optional<User> signInByVerificationCode(String login, String verificationCode) throws ServiceException;

    Optional<User> findUserByLogin(String login) throws ServiceException;

    boolean confirmRegistration(String userEmail, String verificationCode) throws ServiceException;

    String generateUserVerificationCodeByEmail(String userEmail) throws ServiceException;

    boolean matchUserVerificationCode(User user, String verificationCode);
    
    //    boolean blockUser(int userId) throws ServiceException;
}
