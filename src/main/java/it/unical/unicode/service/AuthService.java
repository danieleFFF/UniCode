package it.unical.unicode.service;

import it.unical.unicode.dao.UtenteDAO;
import it.unical.unicode.model.Utente;
import it.unical.unicode.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UtenteDAO utenteDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    public AuthService(UtenteDAO utenteDAO, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.utenteDAO=utenteDAO;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
    }
    public String login(String email, String passwordFornita){
        Optional<Utente> optionalUtente=utenteDAO.findByEmail(email);
        if(optionalUtente.isEmpty()){
            throw new AuthenticationServiceException("Email o password errati.");
        }
        Utente utente=optionalUtente.get();
        String hashSalvato=utente.getPassword_hash();
        if(passwordEncoder.matches(passwordFornita,hashSalvato)){
            return jwtService.generateToken(utente.getEmail());
        }
        else{
            throw new AuthenticationServiceException("Email o password errati.");
        }
    }
}
