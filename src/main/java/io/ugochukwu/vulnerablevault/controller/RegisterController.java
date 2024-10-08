package io.ugochukwu.vulnerablevault.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import io.ugochukwu.vulnerablevault.dao.AccountDAO;
import io.ugochukwu.vulnerablevault.dao.AccountDAOImpl;
import io.ugochukwu.vulnerablevault.dao.RoleDAO;
import io.ugochukwu.vulnerablevault.dao.RoleDAOImpl;
import io.ugochukwu.vulnerablevault.dao.UserDAO;
import io.ugochukwu.vulnerablevault.dao.UserDAOImpl;
import io.ugochukwu.vulnerablevault.entity.Role;
import io.ugochukwu.vulnerablevault.entity.User;

@Controller
public class RegisterController {

	@Autowired
	private UserDAOImpl userDAO;

	@Autowired
	private RoleDAOImpl roleDAO;

	@Autowired
	private AccountDAOImpl accountDAO;

	@RequestMapping("/showRegister")
	public String register(Model model) {
		// Create a user object
		User user = new User();

		// add user object to model
		model.addAttribute("user", user);

		return "register";
	}

//	@RequestMapping("/registerTheUser")
	public String authenticateTheUser(@ModelAttribute("user") User user, BindingResult validationErrors, Model model,
			HttpServletRequest request, HttpServletResponse response) {

//		Input validation
		if (validationErrors.hasErrors()) {
			return "register";
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("errorMessage", "Confirm password does not match password");
			return "register";
		}

		try {
			Role customerRole = roleDAO.retrieveCustomerRole();
			user.setRoleID(customerRole.getId());

			int userId = userDAO.insertUser(user);

			accountDAO.createAccount(userId);

			User registeredUser = userDAO.retrieveUser(user.getEmail());
			if (registeredUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", registeredUser.getEmail());

				return "redirect:/";
			} else {
				model.addAttribute("errorMessage", "An error occurred while registering the user");
				return "register";
			}
		} catch (Exception e) {
			model.addAttribute("errorMessage", "An error occurred while registering the user");
			return "register";
		}
	}
}
