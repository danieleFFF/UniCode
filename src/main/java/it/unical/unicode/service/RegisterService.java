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

        if(userDAO.findByEmail(request.getEmail())!=null){
            throw new IllegalArgumentException("Email already registered");
        }

        if(userDAO.findByUsername(request.getUsername())!=null){
            throw new IllegalArgumentException("Username already registered");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword_hash(passwordEncoder.encode(request.getPassword()));

        userDAO.save(newUser);
    }
}
