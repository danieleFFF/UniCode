package it.unical.unicode.service;

import it.unical.unicode.dao.UtenteDAO;
import it.unical.unicode.dto.RegisterRequest;
import it.unical.unicode.model.Utente;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UtenteDAO utenteDAO;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UtenteDAO utenteDAO, PasswordEncoder passwordEncoder) {
        this.utenteDAO = utenteDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest request) {
        if (utenteDAO.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("User already registered.");
        }

        Utente nuovoUtente = new Utente();
        nuovoUtente.setUsername(request.getUsername());
        nuovoUtente.setEmail(request.getEmail());
        nuovoUtente.setPassword_hash(passwordEncoder.encode(request.getPassword()));

        utenteDAO.save(nuovoUtente);
    }
}
