package it.unical.unicode.service;

import it.unical.unicode.dao.UserDAO;
import it.unical.unicode.model.User;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }
    public void resetPassword(String email, String newPassword) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new AuthenticationServiceException("User not found.");
        }
        String hashedPassword = passwordEncoder.encode(newPassword);
        userDAO.resetPassword(email, hashedPassword);
    }
}
