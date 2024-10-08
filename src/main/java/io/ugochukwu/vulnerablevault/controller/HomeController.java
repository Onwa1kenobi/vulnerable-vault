package io.ugochukwu.vulnerablevault.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.ugochukwu.vulnerablevault.dao.RoleDAOImpl;
import io.ugochukwu.vulnerablevault.dao.UserDAOImpl;
import io.ugochukwu.vulnerablevault.entity.Role;
import io.ugochukwu.vulnerablevault.entity.User;

@Controller
public class HomeController {

	@Autowired
	private UserDAOImpl userDAO;

	@Autowired
	private RoleDAOImpl roleDAO;

	@GetMapping("/")
	public String home(Model model, HttpServletRequest request) {

		String loggedInUserEmail = getPrincipal();
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null && loggedInUserEmail == null) {
			return "redirect:/showLogin";
		}

		User user = userDAO.retrieveUser(loggedInUserEmail);

		if (user != null) {
			Role role = roleDAO.retrieveRoleById(user.getRoleID());

			session.setAttribute("user", user.getEmail());

			switch (role.getName().toUpperCase()) {
			case Role.ADMIN_ROLE:
				return "redirect:/admin-dashboard";
			case Role.TELLER_ROLE:
				return "redirect:/teller-dashboard";
			case Role.CUSTOMER_ROLE:
				return "redirect:/customer-dashboard";
			default:
				return "redirect:/showLogin";
			}
		} else {
			model.addAttribute("errorMessage", "Invalid login");
			return "redirect:/showLogin";
		}
	}

	// Add request mapping for /access-denied
	@GetMapping("/access-denied")
	public String showAccessDenied(HttpServletRequest request) {
		return "access-denied";
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
