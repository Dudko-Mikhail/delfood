package by.dudko.webproject.model.service.impl;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.dao.UserDao;
import by.dudko.webproject.model.dao.impl.UserDaoImpl;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.util.encryption.impl.PasswordEncryptorImpl;
import by.dudko.webproject.validator.UserValidator;

import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final UserServiceImpl INSTANCE = new UserServiceImpl();
    private final UserDao userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {}

    @Override
    public boolean signUp(Map<String, String> userData) throws ServiceException {
        String login = userData.get(RequestParameter.LOGIN);
        UserValidator validator = UserValidator.getInstance();
        String email = userData.get(RequestParameter.EMAIL);
        if (!validator.isValid(userData) || isLoginExists(login) || isEmailExists(email)) {
            return false;
        }
//        if (!validator.isValid(userData)) { FIXME remove if additional actions are not required
//            return false;
//        }
//        if (isLoginExists(login)) {
//            return false;
//        }
//        if (isEmailExists(email)) {
//            return false;
//        }
        var builder = User.getBuilder();
        var encryptor = PasswordEncryptorImpl.getInstance();
        String encryptedPassword = encryptor.encryptPassword(userData.get(RequestParameter.PASSWORD));
        builder.login(login)
                .email(email)
                .firstName(userData.get(RequestParameter.FIRST_NAME))
                .lastName(userData.get(RequestParameter.LAST_NAME))
                .password(encryptedPassword)
                .phoneNumber(userData.get(RequestParameter.PHONE_NUMBER));
        User user = builder.buildUser();
        try {
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to create new user", e);
        }
        return true;
    }

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    private boolean isLoginExists(String login) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findUserByLogin(login);
            return optionalUser.isPresent();
        } catch (DaoException e) {
            throw new ServiceException("Failed to check login existence", e);
        }
    }

    private boolean isEmailExists(String email) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findUserByEmail(email);
            return optionalUser.isPresent();
        } catch (DaoException e) {
            throw new ServiceException("Failed to check email existence", e);
        }
    }
}
