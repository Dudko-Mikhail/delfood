package by.dudko.webproject.validator;

import by.dudko.webproject.controller.RequestAttribute;
import by.dudko.webproject.util.MessageKeys;

import java.util.HashMap;
import java.util.Map;

import static by.dudko.webproject.controller.RequestParameter.EMAIL;
import static by.dudko.webproject.controller.RequestParameter.FIRST_NAME;
import static by.dudko.webproject.controller.RequestParameter.LAST_NAME;
import static by.dudko.webproject.controller.RequestParameter.LOGIN;
import static by.dudko.webproject.controller.RequestParameter.PASSWORD;
import static by.dudko.webproject.controller.RequestParameter.PHONE_NUMBER;
import static by.dudko.webproject.controller.RequestParameter.REPEAT_PASSWORD;

public class UserValidator extends AbstractValidator { // TODO fix regexes.
    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final int EMAIL_MAX_LENGTH = 45;
    private static final int EMAIL_MIN_LENGTH = 3;
    private static final int INITIALS_MAX_LENGTH = 20;
    private static final int LOGIN_MAX_LENGTH = 20;
    private static final String EMAIL_PATTERN = "\\p{Alnum}[\\w-.]*@(\\p{Alnum}[\\w-.]*)?\\w";
    private static final String LOGIN_PATTERN = "([\\w-])+";
    private static final String INITIALS_PATTERN = "([^\\p{Punct}\\s]|[-_. ])+";
    private static final String PHONE_NUMBER_PATTERN = "(25|29|33|44)\\d{7}";

    private static final UserValidator INSTANCE = new UserValidator();

    public static UserValidator getInstance() {
        return INSTANCE;
    }

    public Map<String, String> validate(Map<String, String> userData) {
        String login = userData.get(LOGIN);
        String email = userData.get(EMAIL);
        String password = userData.get(PASSWORD);
        String repeatPassword = userData.get(REPEAT_PASSWORD);
        String phoneNumber = userData.get(PHONE_NUMBER);
        String firstName = userData.get(FIRST_NAME);
        String lastName = userData.get(LAST_NAME);
        HashMap<String, String> report = new HashMap<>();
        if (!isLoginValid(login)) {
            report.put(RequestAttribute.INVALID_LOGIN, MessageKeys.INVALID_FIELD_DATA);
        }
        if (!isEmailValid(email)) {
            report.put(RequestAttribute.INVALID_EMAIL, MessageKeys.INVALID_FIELD_DATA);
        }
        if (!isPasswordValid(password)) {
            report.put(RequestAttribute.INVALID_PASSWORD, MessageKeys.INVALID_FIELD_DATA);
        }
        if (!isPasswordRepeatValid(password, repeatPassword)) {
            report.put(RequestAttribute.INVALID_PASSWORD_REPEAT, MessageKeys.INVALID_PASSWORD_REPEAT);
        }
        if (!isPhoneNumberValid(phoneNumber)) {
            report.put(RequestAttribute.INVALID_PHONE_NUMBER, MessageKeys.INVALID_FIELD_DATA);
        }
        if (!isInitialsValid(firstName)) {
            report.put(RequestAttribute.INVALID_FIRST_NAME, MessageKeys.INVALID_FIELD_DATA);
        }
        if (!isInitialsValid(lastName)) {
            report.put(RequestAttribute.INVALID_LAST_NAME, MessageKeys.INVALID_FIELD_DATA);
        }
        return report;
    }

    public boolean isLoginValid(String login) {
        return isNotNullOrEmpty(login) && maxLengthValidation(login, LOGIN_MAX_LENGTH)
                && login.matches(LOGIN_PATTERN);
    }

    public boolean isEmailValid(String email) {
        return isNotNullOrEmpty(email) && lengthValidation(email, EMAIL_MIN_LENGTH, EMAIL_MAX_LENGTH)
                && email.matches(EMAIL_PATTERN);
    }

    public boolean isPasswordValid(String password) {
        return isNotNullOrEmpty(password) && minLengthValidation(password, PASSWORD_MIN_LENGTH)
                && containsDigit(password) && containsPunctuation(password);
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return isNotNullOrEmpty(phoneNumber) && phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }

    public boolean isInitialsValid(String initials) {
        if (initials == null || initials.isEmpty()) {
            return true;
        }
        return maxLengthValidation(initials, INITIALS_MAX_LENGTH) && initials.matches(INITIALS_PATTERN);
    }

    public boolean isPasswordRepeatValid(String password, String passwordRepeat) {
        return password.equals(passwordRepeat);
    }

    private UserValidator() {}
}
