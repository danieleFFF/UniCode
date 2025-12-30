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
    private final PasswordRecoverService passwordRecoverService;

    public AuthService(UserDAO userDAO, PasswordEncoder passwordEncoder, PasswordRecoverService passwordRecoverService) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.passwordRecoverService = passwordRecoverService;
    }
    public void resetPassword(String email, String newPassword, String secretCode) {
        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new AuthenticationServiceException("User not found.");
        }
        if (!passwordRecoverService.validateResetCode(email, secretCode)) {
            throw new AuthenticationServiceException("Invalid or expired reset code.");
        }
        String hashedPassword = passwordEncoder.encode(newPassword);
        userDAO.resetPassword(email, hashedPassword);
    }
}
