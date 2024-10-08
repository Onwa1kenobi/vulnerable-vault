package io.ugochukwu.vulnerablevault.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.ugochukwu.vulnerablevault.dao.UserDAO;
import io.ugochukwu.vulnerablevault.dao.UserDAOImpl;
import io.ugochukwu.vulnerablevault.entity.User;

@Controller
public class LoginController {

	@Autowired
	private UserDAOImpl userDAO;

	@RequestMapping("/showLogin")
	public String login() {
		return "login";
	}

	@RequestMapping("/authenticateTheUserd")
	public String authenticateTheUser(@RequestParam("username") String username,
			@RequestParam("password") String password, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		if (userDAO.areCredentialsCorrect(username, password)) {
			// Logic for when the user is exists
			User user = userDAO.retrieveUser(username);

			Cookie foo = new Cookie("foo", "bar");

			foo.setHttpOnly(true);
			response.addCookie(foo);

			HttpSession session = request.getSession();
			session.setAttribute("user", user.getEmail());

			return "redirect:/";

			// tony@deakin.edu.au' OR '1'='1--
		} else {
			// Logic for when the user doesn't exist
			model.addAttribute("errorMessage", "Wrong combination of password and username");
			return "login";
		}
	}
	
	@GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = "Wrong combination of password and username";
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = "Wrong combination of password and username";
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// Return the View
		return "login";
	}
}
