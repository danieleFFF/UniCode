package it.unical.unicode.controller;

import it.unical.unicode.model.Esercizio;
import it.unical.unicode.model.TestCase;
import it.unical.unicode.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(idLanguage != null && idLanguage > 0){
            return exerciseService.findByLanguage(idLanguage, sortBy, order);
        } else {
            return exerciseService.findAll(sortBy, order);
        }
    }


    @GetMapping("/{id}")
    public Map<String, Object> getExerciseById(@PathVariable Integer id) {
        Esercizio esercizio = exerciseService.findById(id);
        if (esercizio == null){
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
        return response;
    }

    private String getLanguageName(int idLanguage) {
        switch (idLanguage){
            case 1: return "Python";
            case 2: return "C++";
            case 3: return "Java";
            case 4: return "JavaScript";
            case 5: return "HTML";
            case 6: return "SQL";
            default: return "Unknown";
        }
    }

    @GetMapping("/{id}/tests")
    public List<TestCase> getExerciseTests(@PathVariable Integer id) {
        return exerciseService.findTestsByExerciseId(id);
    }
}
