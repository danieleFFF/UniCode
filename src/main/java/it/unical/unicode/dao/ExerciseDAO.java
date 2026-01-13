package it.unical.unicode.dao;

import it.unical.unicode.model.Exercise;
import it.unical.unicode.model.TestCase;
import java.util.List;

public interface ExerciseDAO {
    List<Exercise> findByLanguage(Integer idLanguage);
    List<Exercise> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size);
    Exercise findById(Integer id);
    List<TestCase> findTestsByExerciseId(Integer id);
    List<Exercise> findAll(String sortBy, String order);
    int save(Exercise exercise);
    void delete(int id);
}
