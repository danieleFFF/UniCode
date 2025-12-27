package it.unical.unicode.controller;

import it.unical.unicode.service.Judge0Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/exercises")
public class RunController {
    @Autowired
    private Judge0Service judge0Service;

    @PostMapping("/{id}/run")
    public Map<String, Object> runCode(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> payload) {
        try {
            System.out.println("Received payload: " + payload);

            Object langIdObj = payload.get("language_id");
            Object codeObj = payload.get("code");
            Object stdinObj = payload.get("stdin");

            if (langIdObj == null || codeObj == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Missing language_id or code");
                errorResponse.put("stdout", "");
                errorResponse.put("stderr", "language_id and code are required");
                return errorResponse;
            }

            String languageId = langIdObj.toString();
            String code = codeObj.toString();
            String stdin = stdinObj != null ? stdinObj.toString() : "";

            if ("82".equals(languageId) && !stdin.isEmpty()) {
                code = stdin + "\n" + code;
                stdin = "";
            }

            System.out.println("Running code for exercise " + id);
            System.out.println("Language ID: " + languageId);
            System.out.println("Code length: " + code.length());
            System.out.println("Input: " + stdin);
            Map<String, Object> result = judge0Service.execute(languageId, code, stdin);

            System.out.println("Judge0 response: " + result);

            Map<String, Object> response = new HashMap<>();
            response.put("stdout", result.get("stdout"));
            response.put("stderr", result.get("stderr"));
            response.put("status", result.get("status"));
            response.put("compile_output", result.get("compile_output"));

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("stdout", "");
            errorResponse.put("stderr", "Internal server error: " + e.getMessage());
            return errorResponse;
        }
    }
}
