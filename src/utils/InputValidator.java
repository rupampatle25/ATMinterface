package utils;

/**
 * Utility for user input validation.
 */
public class InputValidator {

    public static boolean isValidCardNumber(String card) {
        return card != null && card.trim().matches("^\\d{12}$");
    }

    public static boolean isValidPin(String pin) {
        return pin != null && pin.trim().matches("^\\d{4}$");
    }

    public static double parseAmount(String amountStr) {
        if (amountStr == null) {
            return -1;
        }
        String trimmed = amountStr.trim();
        // Require positive numeric format with at most 2 decimal places, rejecting NaN, Infinity, negative, exponents
        if (!trimmed.matches("^\\d+(\\.\\d{1,2})?$")) {
            return -1;
        }
        try {
            double amount = Double.parseDouble(trimmed);
            if (!Double.isFinite(amount) || amount <= 0 || amount > 10_000_000.0) {
                return -1;
            }
            return Math.round(amount * 100.0) / 100.0;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}