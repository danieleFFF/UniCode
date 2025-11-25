package it.unical.unicode.service;

import it.unical.unicode.dao.UserDAO;
import it.unical.unicode.dto.RegisterRequest;
import it.unical.unicode.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest request) {
        if (userDAO.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("User already registered.");
        }

        User nuovoUser = new User();
        nuovoUser.setUsername(request.getUsername());
        nuovoUser.setEmail(request.getEmail());
        nuovoUser.setPassword_hash(passwordEncoder.encode(request.getPassword()));

        userDAO.save(nuovoUser);
    }
}
