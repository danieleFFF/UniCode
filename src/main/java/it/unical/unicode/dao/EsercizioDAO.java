package it.unical.unicode.dao;

import it.unical.unicode.model.Esercizio;
import java.util.List;

public interface EsercizioDAO {
    List<Esercizio> findByLanguage(Integer idLanguage);
    List<Esercizio> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size);

}
