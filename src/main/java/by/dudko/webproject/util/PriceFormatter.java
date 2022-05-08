package by.dudko.webproject.util;

import by.dudko.webproject.model.entity.Language;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatter {
    private PriceFormatter() {
    }

    public static String formatPrice(BigDecimal price, Language language) {
        Locale locale = language.toLocale();
        var formatter = NumberFormat.getInstance(locale);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        return formatter.format(price);
    }

    public static String formatNumber(int number, Language language) {
        Locale locale = language.toLocale();
        var formatter = NumberFormat.getInstance(locale);
        return formatter.format(number);
    }
}
