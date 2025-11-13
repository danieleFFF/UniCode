package it.unical.unicode.dao;

import it.unical.unicode.model.Esercizio;
import it.unical.unicode.model.TestCase;
import java.util.List;

public interface EsercizioDAO {
    List<Esercizio> findByLanguage(Integer idLanguage);
    List<Esercizio> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size);
    Esercizio findById(Integer id);
    List<TestCase> findTestsByExerciseId(Integer id);

    List<Esercizio> findAll(String sortBy, String order);
}
