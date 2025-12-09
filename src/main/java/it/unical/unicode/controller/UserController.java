package it.unical.unicode.controller;

import it.unical.unicode.dto.RegisterRequest;
import it.unical.unicode.model.User;
import it.unical.unicode.service.RegisterService;
import it.unical.unicode.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins="http://localhost:4200")

public class UserController {

    private final RegisterService registerService;
    private final UserService userService;

    public UserController(RegisterService registerService , UserService userService ) {
        this.registerService = registerService;
        this.userService = userService;
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
    public ResponseEntity<User> getProfile(Authentication authentication) {
        try {

            String email = authentication.getName();
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody String newPassword, Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userService.getUserByEmail(email);

            userService.updateUserPassword(user.getId(), newPassword);
            return ResponseEntity.ok("Password updatet successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/avatar")
    public ResponseEntity<String> changeAvatar(@RequestParam int avatarId, Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userService.getUserByEmail(email);

            userService.updateUserAvatar(user.getId(), avatarId);
            return ResponseEntity.ok("Avatar updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMyAccount(Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userService.getUserByEmail(email);

            userService.deleteUser(user.getId());
            return ResponseEntity.ok("Account deleted!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
