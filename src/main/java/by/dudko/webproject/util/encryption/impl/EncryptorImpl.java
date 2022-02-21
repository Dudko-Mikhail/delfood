package by.dudko.webproject.util.encryption.impl;

import by.dudko.webproject.util.encryption.PasswordEncryptor;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptorImpl implements PasswordEncryptor {
    private static PasswordEncryptorImpl instance;

    private PasswordEncryptorImpl() {}

    @Override
    public String encryptPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public boolean matchPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    public static PasswordEncryptor getInstance() {
        if (instance == null) {
            instance = new PasswordEncryptorImpl();
        }
        return instance;
    }
}
