package io.ugochukwu.vulnerablevault.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AccountID")
	protected int id;
	
	@Column(name = "CustomerID")
	protected int customerId;
	
	@Column(name = "AccountNumber")
//	@NotNull(message = "is required")
//	@Size(min = 10, message="is required")
//	@Pattern(regexp ="[a-zA-Z]+", message="incorrect format")
	protected String accountNumber;

	@Column(name = "Balance")
	protected double balance = 0;

	public Account() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int id) {
		this.customerId = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "account = [id: " + getId() + ", account number: " + getAccountNumber()+ ", balance: " + getBalance() + "]";
	}
}
