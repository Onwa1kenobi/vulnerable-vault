package io.ugochukwu.vulnerablevault.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tika.Tika;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.ugochukwu.vulnerablevault.config.CSRFTokenService;
import io.ugochukwu.vulnerablevault.dao.AccountDAOImpl;
import io.ugochukwu.vulnerablevault.dao.TransactionDAOImpl;
import io.ugochukwu.vulnerablevault.dao.UserDAOImpl;
import io.ugochukwu.vulnerablevault.entity.Account;
import io.ugochukwu.vulnerablevault.entity.Transaction;
import io.ugochukwu.vulnerablevault.entity.User;

@Controller
public class TransactionController {

	@Autowired
	private UserDAOImpl userDAO;

	@Autowired
	private AccountDAOImpl accountDAO;

	@Autowired
	private TransactionDAOImpl transactionDAO;

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
    private CSRFTokenService csrfTokenService;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@PostMapping(value = "/performTransaction", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String performTransaction(@RequestParam("transactionType") String transactionType,
			@RequestParam("amount") double amount,
			@RequestParam(value = "destinationAccount", required = false) String destinationAccountNumber,
			@RequestParam(value = "receipt", required = false) MultipartFile receipt, Model model,
			HttpServletRequest request, @RequestParam("csrfToken") String csrfToken) {

		String successMessage = "";
		String errorMessage = "";

		try {
			HttpSession requestSession = request.getSession();
			
			if (!csrfTokenService.validateCsrfToken(requestSession, csrfToken)) {
				HttpSession session = request.getSession(false);
				if (session != null) {
					session.invalidate();
				}
				throw new Exception("Invalid Token!");
	        }

			if (requestSession.getAttribute("user") != null) {
				User user = userDAO.retrieveUser(requestSession.getAttribute("user").toString());
				if (user != null) {
					Account userAccount = accountDAO.retrieveUserAccount(user.getId());
					model.addAttribute("user", user);
					model.addAttribute("account", userAccount);
					double newBalance;
					switch (transactionType) {
					case "deposit":
						Tika tika = new Tika();
						String fileType = tika.detect(receipt.getInputStream());
						System.out.println("File type is: " + fileType);
						if (!"image/png".equals(fileType) && !"image/jpeg".equals(fileType)) {
							errorMessage = "Invalid file type";
						} else {
							byte[] bytes = receipt.getBytes();
							Path path = Paths.get("/tmp/" + receipt.getOriginalFilename());
							Files.write(path, bytes);

							final Transaction transaction = createTransaction(userAccount.getId(), null,
									Transaction.TRANSACTION_TYPE_DEPOSIT, amount, path.toString());
							transactionDAO.insertTransaction(transaction);

							newBalance = userAccount.getBalance() + amount;
							userAccount.setBalance(newBalance);
							accountDAO.updateAccount(userAccount);

							successMessage = "Deposit successful!";
						}
						break;

					case "withdrawal":
						if (userAccount.getBalance() < amount) {
							errorMessage = "Insufficient funds";
						} else {
							final Transaction transaction_ = createTransaction(userAccount.getId(), null,
									Transaction.TRANSACTION_TYPE_WITHDRAWAL, amount, null);
							transactionDAO.insertTransaction(transaction_);

							newBalance = userAccount.getBalance() - amount;
							userAccount.setBalance(newBalance);
							accountDAO.updateAccount(userAccount);
							successMessage = "Withdrawal successful!";
						}
						break;

					case "transfer":
						Account destinationAccount = accountDAO.retrieveAccount(Long.valueOf(destinationAccountNumber));
						if (userAccount.getBalance() < amount) {
							errorMessage = "Insufficient funds";
						} else if (destinationAccount == null) {
							errorMessage = "Invalid destination account";
						} else {
							final Transaction transaction_ = createTransaction(userAccount.getId(),
									destinationAccount.getId(), Transaction.TRANSACTION_TYPE_TRANSFER, amount, null);
							transactionDAO.insertTransaction(transaction_);

							newBalance = userAccount.getBalance() - amount;
							userAccount.setBalance(newBalance);
							accountDAO.updateAccount(userAccount);

							destinationAccount.setBalance(destinationAccount.getBalance() + amount);
							accountDAO.updateAccount(destinationAccount);

							successMessage = "Transfer successful!";
						}
						break;

					default:
						errorMessage = "Invalid transaction type!";
						break;
					}
				}
			}
		} catch (Exception e) {
			errorMessage = "Transaction failed: " + e.getMessage();
		}

		// Add messages to model for display
		model.addAttribute("successMessage", successMessage);
		model.addAttribute("errorMessage", errorMessage);

		// Redirect back to the customer dashboard page
		return "redirect:/customer-dashboard";
	}

	@PostMapping("/deleteTransaction")
	public String deleteTransaction(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = userDAO.retrieveUser(session.getAttribute("user").toString());
			if (user != null) {
				Transaction transaction = (Transaction) model.getAttribute("transaction");
				if (transaction != null) {
					transactionDAO.deleteTransaction(transaction);
					return "redirect:/admin-dashboard";
				}
			}
		}

		model.addAttribute("errorMessage", "Invalid login");
		return "redirect:/showLogin";
	}

	private Transaction createTransaction(int srcAccountId, Integer dstAccountId, String transactionType, double amount,
			String receiptPath) {
		Transaction transaction = new Transaction();
		transaction.setSourceAccountId(srcAccountId);
		transaction.setDestinationAccountId(dstAccountId);
		transaction.setTransactionType(transactionType);
		transaction.setTransactionStatus("Approved");
		transaction.setAmount(amount);
		transaction.setTransactionReceiptPath(receiptPath);
		transaction.setTransactionDate(LocalDateTime.now());
		return transaction;
	}
}
