package it.unical.unicode.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class Judge0Service {
    @Value("${judge0.api-url}")
    private String apiUrl;
    @Value("${judge0.api-key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Object> execute(String languageId, String code, String stdin) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("=>Judge0 Execute<=");
            System.out.println("Language ID: " + languageId);
            System.out.println("Code length: " + (code != null ? code.length() : "null"));
            System.out.println("Stdin: " + stdin);
            String url = apiUrl
                    + "/submissions?base64_encoded=false&wait=true&fields=stdout,stderr,status,compile_output";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-RapidAPI-Key", apiKey);
            headers.set("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("language_id", Integer.parseInt(languageId));
            bodyMap.put("source_code", code);
            bodyMap.put("stdin", stdin != null ? stdin : "");
            String jsonBody = objectMapper.writeValueAsString(bodyMap);
            System.out.println("Request body: " + jsonBody.substring(0, Math.min(200, jsonBody.length())) + "...");
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, request, Map.class);
            System.out.println("Response status: " + responseEntity.getStatusCode());
            System.out.println("Response body: " + responseEntity.getBody());
            Map<String, Object> result = responseEntity.getBody();

            if (result != null) {
                Object stdout = result.get("stdout");
                Object stderr = result.get("stderr");
                Object compileOutput = result.get("compile_output");
                response.put("stdout", stdout != null ? stdout.toString() : "");
                response.put("stderr", stderr != null ? stderr.toString() : "");
                response.put("compile_output", compileOutput != null ? compileOutput.toString() : "");
                response.put("status", result.getOrDefault("status", Map.of("id", 0, "description", "Unknown")));
            } else {
                response.put("stdout", "");
                response.put("stderr", "Empty response from Judge0");
                response.put("compile_output", "");
                response.put("status", Map.of("id", 6, "description", "Error"));
            }
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Error: " + e.getStatusCode());
            System.err.println("Response: " + e.getResponseBodyAsString());
            response.put("stdout", "");
            response.put("stderr", "HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            response.put("compile_output", "");
            response.put("status", Map.of("id", 6, "description", "HTTP Error"));
        } catch (Exception e) {
            System.err.println("Error: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            response.put("stdout", "");
            response.put("stderr", "Error: " + e.getMessage());
            response.put("compile_output", "");
            response.put("status", Map.of("id", 6, "description", "Internal Error"));
        }
        return response;
    }
}
