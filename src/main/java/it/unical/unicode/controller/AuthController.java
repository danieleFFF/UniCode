package it.unical.unicode.controller;

import it.unical.unicode.dto.Credentials;
import it.unical.unicode.dto.LoginRequest;
import it.unical.unicode.dto.JwtResponse;
import it.unical.unicode.service.AuthService;
import it.unical.unicode.service.PasswordRecoverService;
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
    private final PasswordRecoverService passwordRecoverService;

    public AuthController(AuthService authService, PasswordRecoverService passwordRecoverService) {
        this.authService=authService;
        this.passwordRecoverService = passwordRecoverService;
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

    @PostMapping("/send-reset-code")
    public ResponseEntity<Integer> sendPasswordRecoverEmail(@RequestBody String email) {
        Integer exists = passwordRecoverService.sendPasswordRecoverEmail(email);
        return ResponseEntity.ok(exists);
    }
//TODO: Better error handling
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword (@RequestBody Credentials credentials){
        try {
            authService.resetPassword(credentials.getEmail(), credentials.getPassword());
            return ResponseEntity.ok().build();
        }
        catch (AuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }

}