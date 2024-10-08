package io.ugochukwu.vulnerablevault.config;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CSRFTokenService {
    
    private final SecureRandom secureRandom = new SecureRandom();
    
    public String generateCsrfToken(HttpSession session) {
        byte[] randomBytes = new byte[24]; // 24 bytes for 32-character token
        secureRandom.nextBytes(randomBytes);
        String csrfToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        session.setAttribute("CSRF_TOKEN", csrfToken);
        return csrfToken;
    }

    public String getCsrfToken(HttpSession session) {
        return (String) session.getAttribute("CSRF_TOKEN");
    }

    public boolean validateCsrfToken(HttpSession session, String token) {
        String sessionToken = getCsrfToken(session);
        return sessionToken != null && sessionToken.equals(token);
    }
}
