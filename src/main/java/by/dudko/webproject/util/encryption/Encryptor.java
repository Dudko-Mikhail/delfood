package by.dudko.webproject.util.encryption;

public interface Encryptor {
    String encryptPassword(String password);

    boolean matchPassword(String password, String hash);
}
