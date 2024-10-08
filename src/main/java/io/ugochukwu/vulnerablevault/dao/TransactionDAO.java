package io.ugochukwu.vulnerablevault.dao;

import java.util.List;

import io.ugochukwu.vulnerablevault.entity.Transaction;

public interface TransactionDAO {

	public List<Transaction> getTransactions();

	public List<Transaction> getAccountTransactions(int accountId);

	public Transaction getAccountReceiptTransaction(int accountId, String receiptPath);

	public void insertTransaction(Transaction transaction);

	public void deleteTransaction(Transaction transaction);

}