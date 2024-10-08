package io.ugochukwu.vulnerablevault.dao;

import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.ugochukwu.vulnerablevault.entity.Account;

@Repository("accountDao")
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void updateAccount(Account account) {
		Session session = getSession();
		try {
			session.beginTransaction();
			session.update(account);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Override
	public void createAccount(int userId) {
		Account account = new Account();
		account.setCustomerId(userId);
		account.setAccountNumber(generateAccountNumber());

		Session session = getSession();
		try {
			session.beginTransaction();
			session.save(account);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	@Override
	public Account retrieveAccount(long accountNumber) {
		Session session = getSession();
		try {
			session.beginTransaction();

			String hql = "from Account where accountNumber = :accountNumber";
			Query query = session.createQuery(hql);
			query.setParameter("accountNumber", accountNumber);
			List<Account> accounts = query.getResultList();

			return accounts.get(0);
		} finally {
			session.close();
		}
	}

	@Override
	public Account retrieveUserAccount(int userId) {
		Session session = getSession();
		try {
			session.beginTransaction();

			String hql = "from Account where customerId = :customerID";
			Query<Account> query = session.createQuery(hql, Account.class);
			query.setParameter("customerID", userId);
			List<Account> accounts = query.getResultList();
			return accounts.get(0);
		} finally {
			session.close();
		}
	}

	// Method to generate a random 10-digit account number
	private String generateAccountNumber() {
		Random random = new Random();
		long number = 1000000000L + random.nextLong() % 9000000000L; // 10-digit number
		return String.valueOf(number);
	}
}