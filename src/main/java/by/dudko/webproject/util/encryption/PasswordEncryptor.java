package by.dudko.webproject.util.encryption;

public interface PasswordEncryptor {
    String encryptPassword(String password);

    boolean checkPassword(String password, String hash);
}
