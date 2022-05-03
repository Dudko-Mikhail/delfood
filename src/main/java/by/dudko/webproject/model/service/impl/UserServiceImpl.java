package by.dudko.webproject.model.service.impl;

import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.controller.RequestParameter;
import by.dudko.webproject.exception.DaoException;
import by.dudko.webproject.exception.ServiceException;
import by.dudko.webproject.model.dao.UserDao;
import by.dudko.webproject.model.dao.impl.UserDaoImpl;
import by.dudko.webproject.model.entity.User;
import by.dudko.webproject.model.service.UserService;
import by.dudko.webproject.util.i18n.MessageKeys;
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

    private UserServiceImpl() {
    }

    @Override
    public Map<String, String> signUp(Map<String, String> userData) throws ServiceException { // FIXME После окончания того как доделаю валидацию
        UserValidator validator = UserValidator.getInstance();
        Map<String, String> validationReport = validator.validate(userData);
        String login = userData.get(RequestParameter.LOGIN);
        String email = userData.get(RequestParameter.EMAIL);
        if (!validationReport.containsKey(RequestAttribute.INVALID_LOGIN) && isLoginExists(login)) {
            validationReport.put(RequestAttribute.INVALID_LOGIN, MessageKeys.LOGIN_EXISTS);
        }
        if (!validationReport.containsKey(RequestAttribute.INVALID_EMAIL) && isEmailExists(email)) {
            validationReport.put(RequestAttribute.INVALID_EMAIL, MessageKeys.EMAIL_EXISTS);
        }
        if (!validationReport.isEmpty()) {
            return validationReport;
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
            return validationReport;
        } catch (DaoException e) {
            throw new ServiceException("Failed to create new user", e);
        }
    }

    @Override
    public Optional<User> signIn(String loginEmail, String password) throws ServiceException {
        try {
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
    public boolean confirmRegistration(String userEmail, String verificationCode) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findUserByEmail(userEmail);
            if (optionalUser.isEmpty()) {
                return false;
            }
            User user = optionalUser.get();
            if (user.getStatus() != User.Status.UNCONFIRMED
                    || !matchUserVerificationCode(user, verificationCode)) {
                return false;
            }
            return userDao.updateUserStatus(user.getId(), User.Status.ACTIVE);
        } catch (DaoException e) {
            throw new ServiceException("Failed to confirm registration", e);
        }
    }

    @Override
    public String generateUserVerificationCodeByEmail(String userEmail) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findUserByEmail(userEmail);
            User user = optionalUser.orElseThrow(() -> new ServiceException("There is no user with email:" + userEmail));
            String verificationData = generateUserVerificationData(user);
            Encryptor encryptor = EncryptorImpl.getInstance();
            return encryptor.encryptPassword(verificationData);
        } catch (DaoException e) {
            throw new ServiceException("Failed to generate user verification code by email", e);
        }
    }

    @Override
    public boolean matchUserVerificationCode(User user, String verificationCode) {
        String verificationData = generateUserVerificationData(user);
        Encryptor encryptor = EncryptorImpl.getInstance();
        return encryptor.matchPassword(verificationData, verificationCode);
    }

    private String generateUserVerificationData(User user) {
        String email = user.getEmail();
        return email + user.getPassword() + email.hashCode();
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
