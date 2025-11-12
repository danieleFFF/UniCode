package it.unical.unicode.dto;

import lombok.Getter;
import lombok.Setter;

// Questo è un DTO (Data Transfer Object)
// Serve solo a contenere il token in un formato JSON pulito
@Setter
@Getter
public class JwtResponse {

    // A Spring serve un getter per "serializzare" questo in JSON
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

}
