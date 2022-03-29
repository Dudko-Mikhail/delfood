package by.dudko.webproject.util.encryption.impl;

import by.dudko.webproject.util.encryption.Encryptor;
import org.mindrot.jbcrypt.BCrypt;

public class EncryptorImpl implements Encryptor {
    private static EncryptorImpl instance;

    public static Encryptor getInstance() {
        if (instance == null) {
            instance = new EncryptorImpl();
        }
        return instance;
    }

    private EncryptorImpl() {}

    @Override
    public String encryptPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    @Override
    public boolean matchPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
