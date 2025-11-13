package it.unical.unicode.controller;

import it.unical.unicode.service.Judge0Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "http://localhost:4200")
public class RunController {
    @Autowired
    private Judge0Service judge0Service;

    @PostMapping("/{id}/run")
    public Map<String, Object> runCode(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body
    ) {
        try {
            String languageId = body.get("language_id");
            String code = body.get("code");
            String stdin = body.getOrDefault("stdin", "");
            return judge0Service.execute(languageId, code, stdin);
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}
