package it.unical.unicode.controller;

import it.unical.unicode.dto.RegisterRequest;
import it.unical.unicode.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins="http://localhost:4200")

public class UserController {

    private final RegisterService registerService;

    public UserController(RegisterService registerService) {
        this.registerService = registerService;
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
}
