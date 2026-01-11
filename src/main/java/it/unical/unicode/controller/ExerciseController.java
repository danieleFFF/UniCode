package it.unical.unicode.controller;

import it.unical.unicode.dto.ExerciseCreationRequest;
import it.unical.unicode.model.Esercizio;
import it.unical.unicode.model.TestCase;
import it.unical.unicode.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<Esercizio> getExercises(
            @RequestParam(required = false) Integer idLanguage,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        if (idLanguage != null && idLanguage > 0) {
            return exerciseService.findByLanguage(idLanguage, sortBy, order);
        } else {
            return exerciseService.findAll(sortBy, order);
        }
    }

    @GetMapping("/{id}")
    public Map<String, Object> getExerciseById(@PathVariable Integer id) {
        Esercizio esercizio = exerciseService.findById(id);
        if (esercizio == null) {
            throw new RuntimeException("Exercise not found with id: " + id);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", esercizio.getId());
        response.put("title", esercizio.getTitle());
        response.put("description", esercizio.getDescription());
        response.put("difficulty", esercizio.getDifficulty());
        response.put("points", esercizio.getPoints());
        response.put("id_language", esercizio.getId_language());
        String languageName = getLanguageName(esercizio.getId_language());
        response.put("languageName", languageName);
        response.put("solutionDemo", esercizio.getSolution_demo());
        return response;
    }

    private String getLanguageName(int idLanguage) {
        return switch (idLanguage) {
            case 1 -> "Python";
            case 2 -> "C++";
            case 3 -> "SQL";
            case 4 -> "JavaScript";
            case 5 -> "HTML";
            default -> "Unknown";
        };
    }

    @GetMapping("/{id}/tests")
    public List<TestCase> getExerciseTests(@PathVariable Integer id) {
        return exerciseService.findTestsByExerciseId(id);
    }

    @PostMapping
    public ResponseEntity<String> createExercise(@RequestBody ExerciseCreationRequest request) {
        try {
            exerciseService.createExercise(request);
            return ResponseEntity.ok("Exercise created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating exercise: " + e.getMessage());
        }
    }
}
