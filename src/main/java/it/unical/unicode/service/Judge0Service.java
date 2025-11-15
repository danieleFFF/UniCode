package it.unical.unicode.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class Judge0Service {
    private static final String JUDGE0_API = "https://judge0-ce.p.rapidapi.com";
    private static final String API_KEY = "7e8e893e56mshfe345a0bd6f42d9p179171jsn7ac9363d881d";

    public Map<String, Object> execute(String languageId, String code, String stdin) {
        try {
            RestTemplate rest = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> payload = new HashMap<>();
            payload.put("language_id", Integer.parseInt(languageId));
            payload.put("source_code", code);
            payload.put("stdin", stdin);

            String jsonPayload = mapper.writeValueAsString(payload);
            System.out.println("Sending to Judge0: " + jsonPayload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-RapidAPI-Key", API_KEY);
            headers.set("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

            ResponseEntity<String> response = rest.postForEntity(
                    JUDGE0_API + "/submissions?base64_encoded=false&wait=true",
                    request,
                    String.class
            );

            System.out.println("Judge0 response: " + response.getBody());

            Map<String, Object> result = mapper.readValue(response.getBody(), Map.class);

            Map<String, Object> cleanResponse = new HashMap<>();
            cleanResponse.put("stdout", result.getOrDefault("stdout", ""));
            cleanResponse.put("stderr", result.getOrDefault("stderr", ""));
            cleanResponse.put("compile_output", result.getOrDefault("compile_output", ""));
            cleanResponse.put("status", result.getOrDefault("status", Map.of("description", "Unknown")));

            return cleanResponse;

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("stderr", e.getMessage());
            errorResponse.put("stdout", "");
            errorResponse.put("status", Map.of("description", "Error"));
            return errorResponse;
        }
    }
}