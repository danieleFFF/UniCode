package it.unical.unicode.service;

import it.unical.unicode.dao.UtenteDAO;
import it.unical.unicode.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UtenteDAO utenteDAO;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthService(UtenteDAO utenteDAO, PasswordEncoder passwordEncoder) {
        this.utenteDAO = utenteDAO;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean login(String email, String passwordFornita) {
        Optional<Utente> optionalUtente = utenteDAO.findByEmail(email);
        if (optionalUtente.isEmpty()) {
            return false;
        }
        Utente utente = optionalUtente.get();
        String hashSalvato = utente.getPassword_hash();
        return passwordEncoder.matches(passwordFornita, hashSalvato);
    }
}
