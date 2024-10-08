package io.ugochukwu.vulnerablevault.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.ugochukwu.vulnerablevault.dao.AccountDAOImpl;
import io.ugochukwu.vulnerablevault.dao.RoleDAOImpl;
import io.ugochukwu.vulnerablevault.dao.TransactionDAOImpl;
import io.ugochukwu.vulnerablevault.dao.UserDAOImpl;
import io.ugochukwu.vulnerablevault.entity.Account;
import io.ugochukwu.vulnerablevault.entity.Role;
import io.ugochukwu.vulnerablevault.entity.Transaction;
import io.ugochukwu.vulnerablevault.entity.User;

@RestController
public class ImageController {

	@Autowired
	private UserDAOImpl userDAO;

	@Autowired
	private AccountDAOImpl accountDAO;

	@Autowired
	private TransactionDAOImpl transactionDAO;

	@Autowired
	private RoleDAOImpl roleDAO;

	@GetMapping("/uploads/tmp/{filename:.+}")
	public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename, HttpServletRequest request)
			throws Exception {
		HttpSession requestSession = request.getSession();
		if (requestSession.getAttribute("user") == null) {
			throw new Exception("Looks like you are not logged in");
		}

		User user = userDAO.retrieveUser(requestSession.getAttribute("user").toString());
		if (user == null) {
			throw new Exception("Looks like you are not properly logged in");
		}

		Role role = roleDAO.retrieveRoleById(user.getRoleID());
		Account userAccount = accountDAO.retrieveUserAccount(user.getId());

		// Define the path to the image in the /tmp/ directory
		Path imagePath = Paths.get("/tmp/" + filename);

		if (!role.getName().equalsIgnoreCase(Role.TELLER_ROLE) || !role.getName().equalsIgnoreCase(Role.ADMIN_ROLE)) {
			Transaction transaction = transactionDAO.getAccountReceiptTransaction(userAccount.getId(),
					imagePath.toString());
			if (transaction == null) {
				throw new Exception("We could not find what you were looking for");
			}
		}

		// Convert the image file to byte array
		byte[] imageBytes = Files.readAllBytes(imagePath);

		// Create HTTP headers to indicate the content type (image)
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");

		// Return the image as a byte array with the content type header
		return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	}
}
