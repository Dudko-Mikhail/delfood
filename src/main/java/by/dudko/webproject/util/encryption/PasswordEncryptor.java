package by.dudko.webproject.util.encryption;

public interface PasswordEncryptor {
    String encryptPassword(String password);

    boolean matchPassword(String password, String hash);
}
