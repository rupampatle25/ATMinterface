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
        Transaction tx = new Transaction(type, amount, account.getBalance());
        account.getTransactionHistory().add(tx);
    }

    public List<Transaction> getMiniStatement(BankAccount account, int limit) {
        List<Transaction> history = new ArrayList<>(account.getTransactionHistory());
        Collections.reverse(history); // Most recent first
        if (history.size() > limit) {
            return history.subList(0, limit);
        }
        return history;
    }
}