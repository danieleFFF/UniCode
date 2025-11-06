package it.unical.unicode.service;

import it.unical.unicode.dao.ExerciseDao;
import it.unical.unicode.model.Exercise;
import it.unical.unicode.proxy.XpCalculatorProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseDao dao;
    @Autowired
    private XpCalculatorProxy xpProxy;

    public List<Exercise> getExercises(String language, String sortBy, int page) {
        return dao.findByLanguageSorted(language, sortBy, page);
    }

    public int getXp(Exercise exercise, long timeSeconds) {
        return xpProxy.calculateXp(exercise, timeSeconds);
    }
}
