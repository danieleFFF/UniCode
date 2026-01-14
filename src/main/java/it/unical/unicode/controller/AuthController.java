package it.unical.unicode.controller;

import it.unical.unicode.dto.Credentials;
import it.unical.unicode.service.AuthService;
import it.unical.unicode.service.PasswordRecoverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController{
    private final AuthService authService;
    private final PasswordRecoverService passwordRecoverService;

    public AuthController(AuthService authService, PasswordRecoverService passwordRecoverService) {
        this.authService=authService;
        this.passwordRecoverService = passwordRecoverService;
    }

    @PostMapping("/send-reset-code")
    public ResponseEntity<Void> sendPasswordRecoverEmail(@RequestBody String email) {
        passwordRecoverService.sendPasswordRecoverEmail(email);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword (@RequestBody Credentials credentials){
        try {
            authService.resetPassword(credentials.getEmail(), credentials.getPassword(), credentials.getSecretCode());
            return ResponseEntity.ok().build();
        }
        catch (AuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }

}
