package it.unical.unicode.service;

import it.unical.unicode.dto.ExerciseCreationRequest;
import it.unical.unicode.model.Exercise;
import it.unical.unicode.model.TestCase;
import java.util.List;

public interface IExerciseService {
    List<Exercise> findByLanguage(Integer idLanguage, String sortBy, String order);
    List<Exercise> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size);
    Exercise findById(Integer id);
    List<TestCase> findTestsByExerciseId(Integer id);
    List<Exercise> findAll(String sortBy, String order);
    void createExercise(ExerciseCreationRequest request);
}
