package it.unical.unicode.controller;

import it.unical.unicode.dto.LoginRequest;
import it.unical.unicode.dto.JwtResponse;
import it.unical.unicode.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="http://localhost:4200")
public class AuthController{
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService=authService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest){
        try{
            String jwtToken=authService.login(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );
            return ResponseEntity.ok(new JwtResponse(jwtToken));
        }
        catch(AuthenticationServiceException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}