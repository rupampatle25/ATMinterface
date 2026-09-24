package service;

import model.BankAccount;
import model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service handling transaction-related operations.
 */
public class TransactionService {

    public void recordTransaction(BankAccount account, String type, double amount) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        Transaction tx = new Transaction(type, Math.round(amount * 100.0) / 100.0, account.getBalance());
        account.getTransactionHistory().add(tx);
    }

    public List<Transaction> getMiniStatement(BankAccount account, int limit) {
        if (account == null || account.getTransactionHistory() == null || limit <= 0) {
            return Collections.emptyList();
        }
        List<Transaction> history = new ArrayList<>(account.getTransactionHistory());
        Collections.reverse(history); // Most recent first
        if (history.size() > limit) {
            return new ArrayList<>(history.subList(0, limit));
        }
        return history;
    }
}