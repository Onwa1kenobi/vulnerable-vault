package io.ugochukwu.vulnerablevault.config;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

	@ExceptionHandler(ServletException.class)
	public String handleServletException(Model model, HttpServletRequest request, ServletException ex) {
		return handleException(model, request, ex);
	}

	@ExceptionHandler(Exception.class)
	public String handleGeneralException(Model model, HttpServletRequest request, Exception ex) {
		return handleException(model, request, ex);
	}

	private String handleException(Model model, HttpServletRequest request, Exception ex) {
		model.addAttribute("errorMessage", ex.getMessage());

		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("/home/kali/vault_bank.log");
			logger.addHandler(fileHandler);

			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);

			String referer = request.getHeader("referer");

			logger.log(Level.SEVERE, "****Possible attempt of CSRF attack****, Domain = " + referer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error";
	}
}
