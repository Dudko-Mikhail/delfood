package by.dudko.webproject.validator;

import java.util.Map;

public class UserValidator {
    private static final UserValidator INSTANCE = new UserValidator();

    public static UserValidator getInstance() {
        return INSTANCE;
    }

    public boolean isValid(Map<String, String> userRegistrationData) { // TODO
        return true;
    }

    private UserValidator() {}
}
