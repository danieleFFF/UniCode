package it.unical.unicode.service;

import it.unical.unicode.dao.UtenteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteDAO utenteDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;
}