package it.unical.unicode.service;

import it.unical.unicode.dao.UtenteDAO;
import it.unical.unicode.dto.RegisterRequest;
import it.unical.unicode.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UtenteDAO utenteDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
