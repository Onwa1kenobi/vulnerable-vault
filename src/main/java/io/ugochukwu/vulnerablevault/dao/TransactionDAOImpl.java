package io.ugochukwu.vulnerablevault.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.ugochukwu.vulnerablevault.entity.Transaction;

@Repository("transactionDao")
public class TransactionDAOImpl implements TransactionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void insertTransaction(Transaction transaction) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.persist(transaction);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Override
	public List<Transaction> getTransactions() {
		Session session = getSession();
		try {
			session.beginTransaction();

			// Using JOIN to get account numbers based on sourceAccountId and
			// destinationAccountId
			String hql = "SELECT t, a1.accountNumber AS sourceAccountNumber, a2.accountNumber AS destinationAccountNumber "
					+ "FROM Transaction t " + "LEFT JOIN Account a1 ON t.sourceAccountId = a1.id "
					+ "LEFT JOIN Account a2 ON t.destinationAccountId = a2.id";

			List<Object[]> results = session.createQuery(hql, Object[].class).getResultList();

			List<Transaction> transactions = new ArrayList<Transaction>();
			for (Object[] result : results) {
				Transaction transaction = (Transaction) result[0];
				String sourceAccountNumber = (String) result[1];
				String destinationAccountNumber = (String) result[2];
				transaction.setSourceAccountNumber(sourceAccountNumber);
				transaction.setDestinationAccountNumber(destinationAccountNumber);
				transactions.add(transaction);
			}

			return transactions;
		} finally {
			session.close();
		}
	}

	@Override
	public List<Transaction> getAccountTransactions(int accountId) {
		Session session = getSession();
		try {
			session.beginTransaction();

			// Using JOIN to get account numbers based on sourceAccountId and
			// destinationAccountId
			String hql = "SELECT t, a1.accountNumber AS sourceAccountNumber, a2.accountNumber AS destinationAccountNumber "
					+ "FROM Transaction t " + "LEFT JOIN Account a1 ON t.sourceAccountId = a1.id "
					+ "LEFT JOIN Account a2 ON t.destinationAccountId = a2.id "
					+ "where t.sourceAccountId = :accountId";

			List<Object[]> results = session.createQuery(hql, Object[].class).setParameter("accountId", accountId)
					.getResultList();

			List<Transaction> transactions = new ArrayList<Transaction>();
			for (Object[] result : results) {
				Transaction transaction = (Transaction) result[0];
				String sourceAccountNumber = (String) result[1];
				String destinationAccountNumber = (String) result[2];
				transaction.setSourceAccountNumber(sourceAccountNumber);
				transaction.setDestinationAccountNumber(destinationAccountNumber);
				transactions.add(transaction);
			}

			return transactions;
		} finally {
			session.close();
		}
	}

	@Override
	public Transaction getAccountReceiptTransaction(int accountId, String receiptPath) {
		Session session = getSession();
		try {
			session.beginTransaction();

			String hql = "SELECT t, a1.accountNumber AS sourceAccountNumber, a2.accountNumber AS destinationAccountNumber "
					+ "FROM Transaction t " + "LEFT JOIN Account a1 ON t.sourceAccountId = a1.id "
					+ "LEFT JOIN Account a2 ON t.destinationAccountId = a2.id "
					+ "where t.sourceAccountId = :accountId AND t.transactionReceiptPath = :receiptPath";

			List<Object[]> results = session.createQuery(hql, Object[].class).setParameter("accountId", accountId)
					.setParameter("receiptPath", receiptPath).getResultList();

			Transaction transaction = null;
			for (Object[] result : results) {
				transaction = (Transaction) result[0];
				String sourceAccountNumber = (String) result[1];
				String destinationAccountNumber = (String) result[2];
				transaction.setSourceAccountNumber(sourceAccountNumber);
				transaction.setDestinationAccountNumber(destinationAccountNumber);
			}

			return transaction;
		} finally {
			session.close();
		}
	}

	@Override
	public void deleteTransaction(Transaction transaction) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.remove(transaction);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}
}