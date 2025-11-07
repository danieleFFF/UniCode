package it.unical.unicode.service;

import it.unical.unicode.dao.UtenteDAO;
import it.unical.unicode.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UtenteDAO utenteDAO;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthService(UtenteDAO utenteDAO, PasswordEncoder passwordEncoder){
        this.utenteDAO=utenteDAO;
        this.passwordEncoder=passwordEncoder;
    }
    public boolean login(String email, String passwordFornita){
        Utente utente=utenteDAO.findByEmail(email);
        if(utente==null){
            return false;
        }
        String hashSalvato=utente.getPassword_hash();
        return passwordEncoder.matches(passwordFornita, hashSalvato);
    }
}