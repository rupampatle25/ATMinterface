package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the bank account associated with a user.
 */
public class BankAccount {
    private final String accountNumber;
    private double balance;
    private final List<Transaction> transactionHistory;

    public BankAccount(String accountNumber, double initialBalance) {
        if (!Double.isFinite(initialBalance) || initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance must be a non-negative finite number.");
        }
        this.accountNumber = accountNumber;
        this.balance = Math.round(initialBalance * 100.0) / 100.0;
        this.transactionHistory = new ArrayList<>();
        // Record initial deposit
        if (this.balance > 0) {
            transactionHistory.add(new Transaction("Initial Deposit", this.balance, this.balance));
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) {
        if (!Double.isFinite(balance) || balance < 0) {
            throw new IllegalArgumentException("Balance must be a non-negative finite number.");
        }
        this.balance = Math.round(balance * 100.0) / 100.0;
    }
    public List<Transaction> getTransactionHistory() { return transactionHistory; }
}