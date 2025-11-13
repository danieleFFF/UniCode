package it.unical.unicode.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class Judge0Service {
    private static final String JUDGE0_API = "https://api.judge0.com";

    public Map<String, Object> execute(String languageId, String code, String stdin) {
        RestTemplate rest = new RestTemplate();

        Map<String, Object> payload = new HashMap<>();
        payload.put("language_id", Integer.parseInt(languageId));
        payload.put("source_code", code);
        payload.put("stdin", stdin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> response = rest.postForEntity(
                JUDGE0_API + "/submissions?base64_encoded=false&wait=true",
                request,
                Map.class
        );
        return response.getBody();
    }
}
