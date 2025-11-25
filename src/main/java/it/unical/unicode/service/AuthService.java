package it.unical.unicode.service;

import it.unical.unicode.dao.UserDAO;
import it.unical.unicode.model.User;
import it.unical.unicode.security.JwtService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    public AuthService(UserDAO userDAO, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userDAO = userDAO;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }
    public String login(String email, String passwordFornita){
        Optional<User> optionalUtente= userDAO.findByEmail(email);
        if(optionalUtente.isEmpty()){
            throw new AuthenticationServiceException("Email o password errati.");
        }
        User user =optionalUtente.get();
        String hashSalvato= user.getPassword_hash();
        if(passwordEncoder.matches(passwordFornita,hashSalvato)){
            Map<String, Object> extraClaims = new HashMap<>();

            //  Inseriamo l'ID dell'utente
            extraClaims.put("userId", user.getId());

            //  Generiamo il token passando anche la mappa
            return jwtService.generateToken(extraClaims, user.getEmail());
        }
        else{
            throw new AuthenticationServiceException("Email o password errati.");
        }
    }
    public void resetPassword(String email, String newPassword) {
        Optional<User> optionalUtente = userDAO.findByEmail(email);
        if (optionalUtente.isEmpty()) {
            throw new AuthenticationServiceException("User not found.");
        }
        String hashedPassword = passwordEncoder.encode(newPassword);
        userDAO.resetPassword(email, hashedPassword);
    }
}
