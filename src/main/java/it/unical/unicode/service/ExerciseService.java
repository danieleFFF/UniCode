package it.unical.unicode.service;

import it.unical.unicode.dao.EsercizioDAO;
import it.unical.unicode.dao.TestCaseDAO;
import it.unical.unicode.dto.ExerciseCreationRequest;
import it.unical.unicode.model.Esercizio;
import it.unical.unicode.model.TestCase;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;

@Service
public class ExerciseService {
    private final EsercizioDAO esercizioDAO;
    private final TestCaseDAO testCaseDAO;

    public ExerciseService(EsercizioDAO esercizioDAO, TestCaseDAO testCaseDAO) {
        this.esercizioDAO = esercizioDAO;
        this.testCaseDAO = testCaseDAO;
    }

    public List<Esercizio> findByLanguage(Integer idLanguage, String sortBy, String order) {
        List<Esercizio> esercizi = esercizioDAO.findByLanguage(idLanguage);

        Comparator<Esercizio> comparator = switch (sortBy) {
            case "difficulty" -> Comparator.comparingInt(e -> getDifficultyOrder(e.getDifficulty()));
            case "points" -> Comparator.comparing(Esercizio::getPoints);
            default -> Comparator.comparing(Esercizio::getTitle, String.CASE_INSENSITIVE_ORDER);
        };

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        esercizi.sort(comparator);
        return esercizi;
    }

    private int getDifficultyOrder(String difficulty) {
        return switch (difficulty) {
            case "Easy" -> 1;
            case "Medium" -> 2;
            case "Hard" -> 3;
            default -> 0;
        };
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

    public void createExercise(ExerciseCreationRequest request) {
        int newId = esercizioDAO.save(request.getExercise());
        if (request.getTestCases() != null && !request.getTestCases().isEmpty()) {
            testCaseDAO.saveAll(request.getTestCases(), newId);
        }
    }
}
