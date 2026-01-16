package it.unical.unicode.dao;

import it.unical.unicode.model.TestCase;
import java.util.List;

public interface TestCaseDAO {
    void saveAll(List<TestCase> tests, int exerciseId);
    void deleteByExerciseId(int exerciseId);
}
