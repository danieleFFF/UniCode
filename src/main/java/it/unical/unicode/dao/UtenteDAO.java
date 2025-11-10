package it.unical.unicode.dao;

import it.unical.unicode.model.Utente;

import java.util.Optional;

public interface UtenteDAO {

    void save(Utente utente);
    Optional<Utente> findByEmail(String email);
}
