package it.unical.unicode.controller;

import it.unical.unicode.model.Esercizio;
import it.unical.unicode.model.TestCase;
import it.unical.unicode.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "http://localhost:4200")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public List<Esercizio> getExercises(
            @RequestParam(required = false) Integer idLanguage,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        if (idLanguage != null && idLanguage > 0) {
            return exerciseService.findByLanguage(idLanguage, sortBy, order);
        } else {
            return exerciseService.findAll(sortBy, order);
        }
    }


    @GetMapping("/{id}")
    public Esercizio getExerciseById(@PathVariable Integer id) {
        Esercizio esercizio = exerciseService.findById(id);
        if (esercizio == null) {
            throw new RuntimeException("Exercise not found with id: " + id);
        }
        return esercizio;
    }

    @GetMapping("/{id}/tests")
    public List<TestCase> getExerciseTests(@PathVariable Integer id) {
        return exerciseService.findTestsByExerciseId(id);
    }
}
