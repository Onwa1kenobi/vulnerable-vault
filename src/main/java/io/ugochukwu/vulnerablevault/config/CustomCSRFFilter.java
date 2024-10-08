package io.ugochukwu.vulnerablevault.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomCSRFFilter extends OncePerRequestFilter {

	private static final String BASE_URL = "http://localhost:8081/vulnerable-vault/";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String referer = request.getHeader("referer");
		String origin = request.getHeader("origin");

		System.out.println("Referer is: " + referer);
		System.out.println("Origin is: " + origin);
		System.out.println("Request URL is: " + request.getRequestURI());

		// Check if the request is for the login page
		if (request.getRequestURI().toLowerCase().contains("login") || request.getRequestURI().equals("/vulnerable-vault/")) {
			System.out.println("Login or Base page accessed.");
			filterChain.doFilter(request, response); // Continue processing
			return;
		} else {
			System.out.println("Login or Base page accessed.");
			filterChain.doFilter(request, response); // Continue processing
			return;
		}

		/*/ Check if referer or origin is valid
		if (isValidReferer(referer) || isValidOrigin(origin)) {
			filterChain.doFilter(request, response); // Continue processing
		} else {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}
			throw new ServletException("CSRF attack detected");
		}*/
	}

	private boolean isValidReferer(String referer) {
		return referer != null && referer.startsWith(BASE_URL);
	}

	private boolean isValidOrigin(String origin) {
		return origin != null && origin.equals(BASE_URL);
	}
}
