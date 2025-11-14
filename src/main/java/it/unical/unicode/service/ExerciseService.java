package it.unical.unicode.service;

import it.unical.unicode.dao.EsercizioDAO;
import it.unical.unicode.model.Esercizio;
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

        // --- Ordinamento lato Java ---
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

}
