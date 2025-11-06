package it.unical.unicode.controller;

import it.unical.unicode.model.Exercise;
import it.unical.unicode.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "*")
public class ExerciseController {
    @Autowired
    private ExerciseService service;

    @GetMapping
    public List<Exercise> getExercises(
            @RequestParam String language,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "0") int page) {
        return service.getExercises(language, sortBy, page);
    }
}
