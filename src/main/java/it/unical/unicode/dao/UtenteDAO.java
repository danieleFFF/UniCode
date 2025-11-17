package it.unical.unicode.dao;

import it.unical.unicode.model.Utente;
import java.util.List;
import java.util.Optional;

public interface UtenteDAO {

    void save(Utente utente);
    Optional<Utente> findByEmail(String email);
    void resetPassword(String email, String newPassword);
    Utente findById(int id);
    List<Utente> findAll();
    void updateTotalPoints(int userId, int pointsToAdd);
    List<Utente> getRanking(int limit);
}
