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

    //stdin = testcase's input
    public Map<String, Object> execute(String languageId, String code, String stdin)  {
        Map<String, Object> response = new HashMap<>();

        //Prepares data to be sent to Judge0
        try {
            String url = apiUrl + "/submissions?base64_encoded=false&wait=true&fields=stdout,stderr,status,compile_output";
            //Credentials to log into rapidAPI
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-RapidAPI-Key", apiKey);
            headers.set("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");
            //Request's body
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("language_id", Integer.parseInt(languageId)); //Programing language chosen by the user
            bodyMap.put("source_code", code); //User's code
            bodyMap.put("stdin", stdin != null ? stdin : ""); //Test's input
            String jsonBody = objectMapper.writeValueAsString(bodyMap);
            //Sends POST request to Judge0 and receives answer
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> result = responseEntity.getBody();

            if(result != null){
                Object stdout = result.get("stdout"); //What the user's code printed
                Object stderr = result.get("stderr"); //Potential errors
                Object compileOutput = result.get("compile_output"); //Compilation errors
                //Put into the response that will be returned
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
        } catch (HttpClientErrorException e) { //Http error
            System.err.println("HTTP Error: " + e.getStatusCode());
            System.err.println("Response: " + e.getResponseBodyAsString());
            response.put("stdout", "");
            response.put("stderr", "HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            response.put("compile_output", "");
            response.put("status", Map.of("id", 6, "description", "HTTP Error"));
        } catch (Exception e) { //Other errors
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
