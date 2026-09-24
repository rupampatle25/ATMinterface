package utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility for formatting currency.
 */
public class CurrencyFormatter {
    private static final Locale INDIA_LOCALE = Locale.forLanguageTag("en-IN");

    public static String format(double amount) {
        if (!Double.isFinite(amount)) {
            return "₹0.00";
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(INDIA_LOCALE);
        return format.format(amount);
    }
}