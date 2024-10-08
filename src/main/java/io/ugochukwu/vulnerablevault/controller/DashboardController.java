package io.ugochukwu.vulnerablevault.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import io.ugochukwu.vulnerablevault.config.CSRFTokenService;
import io.ugochukwu.vulnerablevault.dao.AccountDAOImpl;
import io.ugochukwu.vulnerablevault.dao.TransactionDAOImpl;
import io.ugochukwu.vulnerablevault.dao.UserDAOImpl;
import io.ugochukwu.vulnerablevault.entity.Account;
import io.ugochukwu.vulnerablevault.entity.Transaction;
import io.ugochukwu.vulnerablevault.entity.User;

@Controller
public class DashboardController {

	@Autowired
	private UserDAOImpl userDAO;

	@Autowired
	private AccountDAOImpl accountDAO;

	@Autowired
	private TransactionDAOImpl transactionDAO;

	@Autowired
	private CSRFTokenService csrfTokenService;

	static Logger logger = Logger.getLogger(DashboardController.class.getName());

	@GetMapping("/customer-dashboard")
	public String customerDashboard(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = userDAO.retrieveUser(session.getAttribute("user").toString());
			if (user != null) {
				Account account = accountDAO.retrieveUserAccount(user.getId());

				List<Transaction> transactions = transactionDAO.getAccountTransactions(account.getId());

				session.setAttribute("user", user.getEmail());

				// add user, account, and transactions objects to model
				model.addAttribute("user", user);
				model.addAttribute("account", account);
				model.addAttribute("transactions", transactions);

				String csrfToken = csrfTokenService.generateCsrfToken(session);
				model.addAttribute("csrfToken", csrfToken);
				
				return "customer-dashboard";
			}
		}

		model.addAttribute("errorMessage", "Invalid login");
		return "redirect:/showLogin";
	}

	@GetMapping("/teller-dashboard")
	public String tellerDashboard(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = userDAO.retrieveUser(session.getAttribute("user").toString());
			if (user != null) {
				List<Transaction> transactions = transactionDAO.getTransactions();

				session.setAttribute("user", user.getEmail());

				// add user and account objects to model
				model.addAttribute("user", user);
				model.addAttribute("transactions", transactions);

				String csrfToken = csrfTokenService.generateCsrfToken(session);
				model.addAttribute("csrfToken", csrfToken);
				
				return "teller-dashboard";
			}
		}

		model.addAttribute("errorMessage", "Invalid login");
		return "redirect:/showLogin";
	}

	@GetMapping("/admin-dashboard")
	public String adminDashboard(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") != null) {
			User user = userDAO.retrieveUser(session.getAttribute("user").toString());
			if (user != null) {
				List<Transaction> transactions = transactionDAO.getTransactions();

				session.setAttribute("user", user.getEmail());

				// add user and account objects to model
				model.addAttribute("user", user);
				model.addAttribute("transactions", transactions);

				String csrfToken = csrfTokenService.generateCsrfToken(session);
				model.addAttribute("csrfToken", csrfToken);
				
				return "admin-dashboard";
			}
		}

		model.addAttribute("errorMessage", "Invalid login");
		return "redirect:/showLogin";
	}
	
	@ExceptionHandler(RuntimeException.class)
	public String errorHandler(Model model, HttpServletRequest request, RuntimeException ex)
			throws SecurityException, IOException {
		model.addAttribute("errorMessage", ex.getMessage());

		FileHandler fileHandler = new FileHandler("/home/kali/vault_bank.log");
		logger.addHandler(fileHandler);

		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);

		String referer = request.getHeader("referer");

		logger.log(Level.SEVERE, "****Possible attempt of CSRF attack****, Domain = " + referer);

		return "error";
	}
}
