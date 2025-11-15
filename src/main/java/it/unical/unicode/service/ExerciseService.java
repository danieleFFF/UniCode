package it.unical.unicode.service;

import it.unical.unicode.dao.EsercizioDAO;
import it.unical.unicode.model.Esercizio;
import it.unical.unicode.model.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class ExerciseService {
    private final EsercizioDAO esercizioDAO;
    public ExerciseService(EsercizioDAO esercizioDAO) {
        this.esercizioDAO = esercizioDAO;
    }

    public List<Esercizio> findByLanguage(Integer idLanguage, String sortBy, String order) {
        List<Esercizio> esercizi = esercizioDAO.findByLanguage(idLanguage);

        Comparator<Esercizio> comparator = switch (sortBy) {
            case "difficulty" -> Comparator.comparing(Esercizio::getDifficulty, String.CASE_INSENSITIVE_ORDER);
            case "points" -> Comparator.comparing(Esercizio::getPoints);
            default -> Comparator.comparing(Esercizio::getTitle, String.CASE_INSENSITIVE_ORDER);
        };

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        esercizi.sort(comparator);
        return esercizi;
    }

    public List<Esercizio> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size) {
        List<Esercizio> esercizi = esercizioDAO.findByLanguagePaged(idLanguage, sortBy, order, page, size);
        return esercizi;
    }

    public Esercizio findById(Integer id) {
        return esercizioDAO.findById(id);
    }

    public List<TestCase> findTestsByExerciseId(Integer id) {
        return esercizioDAO.findTestsByExerciseId(id);
    }

    public List<Esercizio> findAll(String sortBy, String order) {
        return esercizioDAO.findAll(sortBy, order);
    }
}
