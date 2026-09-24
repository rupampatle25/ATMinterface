package utils;

/**
 * Utility for user input validation.
 */
public class InputValidator {

    public static boolean isValidCardNumber(String card) {
        return card != null && card.matches("\\d{12}");
    }

    public static boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public static double parseAmount(String amountStr) {
        try {
            return Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}