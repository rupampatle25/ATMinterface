package utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility for formatting currency.
 */
public class CurrencyFormatter {
    public static String format(double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        return format.format(amount);
    }
}