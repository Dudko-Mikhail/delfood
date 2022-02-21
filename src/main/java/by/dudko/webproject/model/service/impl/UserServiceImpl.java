package by.dudko.webproject.model.service.impl;

import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.dao.UserDao;
import by.dudko.webproject.model.dao.impl.UserDaoImpl;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.util.encryption.Encryptor;
import by.dudko.webproject.util.encryption.impl.EncryptorImpl;
import by.dudko.webproject.validator.UserValidator;

import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final UserServiceImpl INSTANCE = new UserServiceImpl();
    private final UserDao userDao = UserDaoImpl.getInstance();

    public static UserServiceImpl getInstance() {
        return INSTANCE;
    }

    private UserServiceImpl() {}

    @Override
    public Optional<String> signUp(Map<String, String> userData) throws ServiceException {
        UserValidator validator = UserValidator.getInstance();
        if (!validator.isValid(userData)) { // TODO add validation report
            return Optional.empty();
        }
        String login = userData.get(RequestParameter.LOGIN);
        if (isLoginExists(login)) { // TODO add request attribute
            return Optional.empty();
        }
        String email = userData.get(RequestParameter.EMAIL);
        if (isEmailExists(email)) { // TODO add request attribute
            return Optional.empty();
        }
        var builder = User.getBuilder();
        var encryptor = EncryptorImpl.getInstance();
        String encryptedPassword = encryptor.encryptPassword(userData.get(RequestParameter.PASSWORD));
        builder.login(login)
                .email(email)
                .firstName(userData.get(RequestParameter.FIRST_NAME))
                .lastName(userData.get(RequestParameter.LAST_NAME))
                .password(encryptedPassword)
                .phoneNumber(userData.get(RequestParameter.PHONE_NUMBER))
                .role(User.Role.CLIENT);
        User user = builder.buildUser();
        try {
            userDao.create(user);
            return Optional.of(encryptor.generateUserVerificationCode(user));
        } catch (DaoException e) {
            throw new ServiceException("Failed to create new user", e);
        }
    }

    @Override
    public Optional<User> signIn(String loginEmail, String password) throws ServiceException {
        try { // TODO добавить валидацию
            var encryptor = EncryptorImpl.getInstance();
            Optional<User> optionalUser = userDao.findUserByLoginOrEmail(loginEmail);
            if (optionalUser.isEmpty()) {
                return Optional.empty();
            }
            String encryptedPassword = optionalUser.get().getPassword();
            return encryptor.matchPassword(password, encryptedPassword) ? optionalUser : Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException("Failed to sign in user", e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws ServiceException {
        try {
            return userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find user by login", e);
        }
    }

    @Override
    public boolean confirmRegistration(String login, String verificationCode) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findUserByLogin(login);
            if (optionalUser.isEmpty()) {
                return false;
            }
            User user = optionalUser.get();
            Encryptor encryptor = EncryptorImpl.getInstance();
            if (!encryptor.matchUserVerificationCode(user, verificationCode)) {
                return false;
            }
            return userDao.updateUserStatus(user.getId(), User.Status.ACTIVE);
        } catch (DaoException e) {
            throw new ServiceException("Failed to confirm registration", e);
        }
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
