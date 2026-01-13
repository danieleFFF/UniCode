package it.unical.unicode.service;

import it.unical.unicode.dao.ExerciseDAO;
import it.unical.unicode.dao.TestCaseDAO;
import it.unical.unicode.dto.ExerciseCreationRequest;
import it.unical.unicode.model.Exercise;
import it.unical.unicode.model.TestCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service("exerciseServiceImpl")
public class ExerciseService implements IExerciseService {
    private final ExerciseDAO exerciseDAO;
    private final TestCaseDAO testCaseDAO;

    public ExerciseService(ExerciseDAO exerciseDAO, TestCaseDAO testCaseDAO) {
        this.exerciseDAO = exerciseDAO;
        this.testCaseDAO = testCaseDAO;
    }

    public List<Exercise> findByLanguage(Integer idLanguage, String sortBy, String order) {
        List<Exercise> esercizi = exerciseDAO.findByLanguage(idLanguage);

        Comparator<Exercise> comparator = switch (sortBy){
            case "difficulty" -> Comparator.comparingInt(e -> getDifficultyOrder(e.getDifficulty()));
            case "points" -> Comparator.comparing(Exercise::getPoints);
            default -> Comparator.comparing(Exercise::getTitle, String.CASE_INSENSITIVE_ORDER);
        };

        if("desc".equalsIgnoreCase(order)) comparator = comparator.reversed();
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

    public List<Exercise> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size){

        return exerciseDAO.findByLanguagePaged(idLanguage, sortBy, order, page, size);
    }

    public Exercise findById(Integer id){
        return exerciseDAO.findById(id);
    }

    public List<TestCase> findTestsByExerciseId(Integer id) {
        return exerciseDAO.findTestsByExerciseId(id);
    }

    public List<Exercise> findAll(String sortBy, String order){
        return exerciseDAO.findAll(sortBy, order);
    }

    public void createExercise(ExerciseCreationRequest request){
        int newId = exerciseDAO.save(request.getExercise());
        if (request.getTestCases() != null && !request.getTestCases().isEmpty()) {
            testCaseDAO.saveAll(request.getTestCases(), newId);
        }
    }

    @Transactional
    public void deleteExercise(int id) {
        testCaseDAO.deleteByExerciseId(id);
        exerciseDAO.delete(id);
    }
}
