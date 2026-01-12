package it.unical.unicode.controller;

import it.unical.unicode.dto.ExerciseCreationRequest;
import it.unical.unicode.model.Exercise;
import it.unical.unicode.model.TestCase;
import it.unical.unicode.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public List<Exercise> getExercises(
            @RequestParam(required = false) Integer idLanguage,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {

        if (idLanguage != null && idLanguage > 0)
            return exerciseService.findByLanguage(idLanguage, sortBy, order);
        else
            return exerciseService.findAll(sortBy, order);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getExerciseById(@PathVariable Integer id) {
        Exercise exercise = exerciseService.findById(id);

        if (exercise == null)
            throw new RuntimeException("Exercise not found with id: " + id);

        Map<String, Object> response = new HashMap<>();
        response.put("id", exercise.getId());
        response.put("title", exercise.getTitle());
        response.put("description", exercise.getDescription());
        response.put("difficulty", exercise.getDifficulty());
        response.put("points", exercise.getPoints());
        response.put("id_language", exercise.getId_language());
        String languageName = getLanguageName(exercise.getId_language());
        response.put("languageName", languageName);
        response.put("solutionDemo", exercise.getSolution_demo());

        return response;
    }

    private String getLanguageName(int idLanguage){
        return switch (idLanguage) {
            case 1 -> "Python";
            case 2 -> "Java";
            case 3 -> "C++";
            case 4 -> "HTML";
            case 5 -> "JavaScript";
            case 6 -> "SQL";
            default -> "Unknown";
        };
    }

    @GetMapping("/{id}/tests")
    public List<TestCase> getExerciseTests(@PathVariable Integer id) {
        return exerciseService.findTestsByExerciseId(id);
    }

    @PostMapping
    public ResponseEntity<String> createExercise(@RequestBody ExerciseCreationRequest request){
        try {
            exerciseService.createExercise(request);
            return ResponseEntity.ok("Exercise created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating exercise: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExercise(@PathVariable Integer id) {
        try {
            exerciseService.deleteExercise(id);
            return ResponseEntity.ok("Exercise deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting exercise: " + e.getMessage());
        }
    }
}
