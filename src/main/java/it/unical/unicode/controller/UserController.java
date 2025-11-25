package it.unical.unicode.controller;

import it.unical.unicode.dto.RegisterRequest;
import it.unical.unicode.dto.UserDTO;
import it.unical.unicode.model.User;
import it.unical.unicode.security.JwtService;
import it.unical.unicode.service.RegisterService;
import it.unical.unicode.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins="http://localhost:4200")

public class UserController {

    private final RegisterService registerService;
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(RegisterService registerService , UserService userService , JwtService jwtService) {
        this.registerService = registerService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            registerService.registerUser(registerRequest);
            return ResponseEntity.ok("User successfully registered.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String authHeader) {
        try {
            // 1. Puliamo il token (togliamo "Bearer ")
            String token = authHeader.substring(7);

            // 2. Estraiamo l'ID che abbiamo messo nel token durante il login
            Integer userId = jwtService.extractUserId(token);

            // 3. Cerchiamo l'utente nel DB usando l'ID estratto
            // Assicurati di avere un metodo che cerca per ID nel service
            User user = userService.getUserById(userId);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
