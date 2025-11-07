package it.unical.unicode.controller;

import it.unical.unicode.dto.LoginRequest;
import it.unical.unicode.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        boolean loginValido=authService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        if(loginValido){
            return ResponseEntity.ok("Login effettuato con successo!");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o password errati.");
        }
    }
}