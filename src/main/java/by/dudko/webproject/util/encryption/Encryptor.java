package by.dudko.webproject.util.encryption;

import by.dudko.webproject.model.entity.User;

public interface Encryptor {
    String encryptPassword(String password);

    boolean matchPassword(String password, String hash);

    String generateUserVerificationCode(User user);

    boolean matchUserVerificationCode(User user, String verificationCode);
}
