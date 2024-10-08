package io.ugochukwu.vulnerablevault.dao;

import io.ugochukwu.vulnerablevault.entity.Account;

public interface AccountDAO {
	
	public void updateAccount(Account account);

	public void createAccount(int userId);

	public Account retrieveAccount(long accountNumber);

	public Account retrieveUserAccount(int userId);

}