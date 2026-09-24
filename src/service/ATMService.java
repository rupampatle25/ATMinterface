package service;

import model.BankAccount;
import model.User;

/**
 * Core service layer bridging UI and Models.
 */
public class ATMService {
    private User currentUser;
    private final TransactionService transactionService;

    // Simulating database with an in-memory user
    private final User databaseUser;

    public ATMService() {
        this.transactionService = new TransactionService();
        BankAccount acc = new BankAccount("ACC-987654321", 50000.00);
        this.databaseUser = new User("John Doe", "123456789012", "1234", acc);
    }

    public boolean login(String cardNumber, String pin) {
        if (databaseUser.getCardNumber().equals(cardNumber) && databaseUser.getPin().equals(pin)) {
            this.currentUser = databaseUser;
            return true;
        }
        return false;
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        BankAccount account = currentUser.getAccount();
        account.setBalance(account.getBalance() + amount);
        transactionService.recordTransaction(account, "Deposit", amount);
    }

    public void withdraw(double amount) throws IllegalArgumentException {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        BankAccount account = currentUser.getAccount();
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        account.setBalance(account.getBalance() - amount);
        transactionService.recordTransaction(account, "Withdrawal", amount);
    }

    public void changePin(String oldPin, String newPin) throws IllegalArgumentException {
        if (!currentUser.getPin().equals(oldPin)) {
            throw new IllegalArgumentException("Incorrect Current PIN.");
        }
        currentUser.setPin(newPin);
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }
}