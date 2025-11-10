package it.unical.unicode.controller;

import it.unical.unicode.model.Esercizio;
import it.unical.unicode.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "http://localhost:4200")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<Esercizio>> getExercises(
            @RequestParam(name = "idLanguage", required = false) Integer idLanguage,
            @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        List<Esercizio> esercizi = exerciseService.findByLanguagePaged(idLanguage, sortBy, order, page, size);
        return ResponseEntity.ok(esercizi);
    }

}
