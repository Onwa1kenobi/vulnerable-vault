package io.ugochukwu.vulnerablevault.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
public class Transaction {

	public static final String TRANSACTION_TYPE_DEPOSIT = "DEPOSIT";
	public static final String TRANSACTION_TYPE_WITHDRAWAL = "WITHDRAWAL";
	public static final String TRANSACTION_TYPE_TRANSFER = "TRANSFER";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TransactionID")
	private int id;

	@Column(name = "SourceAccountID")
	private Integer sourceAccountId;

	@Column(name = "DestinationAccountID")
	private Integer destinationAccountId; // For transfer transactions

	@Column(name = "TransactionDate", nullable = false)
	private LocalDateTime transactionDate;

	@Column(name = "TransactionType", nullable = false, length = 20)
	private String transactionType; // E.g., Deposit, Withdrawal, Transfer

	@Column(name = "TransactionStatus", nullable = false, length = 20)
	private String transactionStatus; // E.g., Pending, Approved, Denied

	@Column(name = "Amount", nullable = false)
	private double amount;

	@Column(name = "TransactionReceiptPath", length = 250)
	private String transactionReceiptPath; // File path for deposit receipt

	transient protected String sourceAccountNumber, destinationAccountNumber;

	public Transaction() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getSourceAccountId() {
		return sourceAccountId;
	}

	public void setSourceAccountId(Integer sourceAccountId) {
		this.sourceAccountId = sourceAccountId;
	}

	public Integer getDestinationAccountId() {
		return destinationAccountId;
	}

	public void setDestinationAccountId(Integer destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTransactionReceiptPath() {
		return transactionReceiptPath;
	}

	public void setTransactionReceiptPath(String transactionReceiptPath) {
		this.transactionReceiptPath = transactionReceiptPath;
	}

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String accountNumber) {
		this.sourceAccountNumber = accountNumber;
	}

	public String getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(String accountNumber) {
		this.destinationAccountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "Transaction{" + "id=" + id + ", sourceAccountId=" + sourceAccountId + ", destinationAccountId="
				+ destinationAccountId + ", transactionDate=" + transactionDate + ", transactionType='"
				+ transactionType + '\'' + ", transactionStatus=" + transactionStatus + ", amount=" + amount
				+ ", transactionReceiptPath='" + transactionReceiptPath + '\'' + '}';
	}
}
