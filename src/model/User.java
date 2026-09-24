package model;

/**
 * Represents an ATM User.
 */
public class User {
    private final String name;
    private final String cardNumber;
    private String pin;
    private final BankAccount account;

    public User(String name, String cardNumber, String pin, BankAccount account) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.account = account;
    }

    public String getName() { return name; }
    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public void setPin(String pin) {
        if (pin == null || !pin.trim().matches("^\\d{4}$")) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits.");
        }
        this.pin = pin.trim();
    }
    public BankAccount getAccount() { return account; }
}