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
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        // Record initial deposit
        if (initialBalance > 0) {
            transactionHistory.add(new Transaction("Initial Deposit", initialBalance, initialBalance));
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public List<Transaction> getTransactionHistory() { return transactionHistory; }
}