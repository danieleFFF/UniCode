package it.unical.unicode.dao;

import it.unical.unicode.model.Utente;

public interface UtenteDAO {
    Utente findByEmail(String email);
}
