package by.dudko.webproject.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractValidator {
    protected AbstractValidator() {}

    protected boolean lengthValidation(String string, int minLength, int maxLength) {
        int length = string.length();
        return length >= minLength && length <= maxLength;
    }

    protected boolean containsDigit(String text) {
        Pattern digitPattern = Pattern.compile("\\d");
        Matcher matcher = digitPattern.matcher(text);
        return matcher.find();
    }

    protected boolean containsPunctuation(String text) {
        Pattern punctuationPattern = Pattern.compile("\\p{Punct}");
        Matcher matcher = punctuationPattern.matcher(text);
        return matcher.find();
    }

    protected boolean maxLengthValidation(String string, int maxLength) {
        return string.length() <= maxLength;
    }

    protected boolean minLengthValidation(String string, int minLength) {
        return string.length() >= minLength;
    }

    protected boolean isNotNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
