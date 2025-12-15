package it.unical.unicode.controller;

import it.unical.unicode.dto.ChangePasswordDTO;
import it.unical.unicode.dto.Credentials;
import it.unical.unicode.dto.RegisterRequest;
import it.unical.unicode.model.User;
import it.unical.unicode.service.RegisterService;
import it.unical.unicode.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RegisterService registerService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(RegisterService registerService , UserService userService, PasswordEncoder passwordEncoder) {
        this.registerService = registerService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO request, Authentication authentication) {
        try {
            String email = authentication.getName();
            User user = userService.getUserByEmail(email);

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword_hash())) {
                return ResponseEntity.badRequest().body("Password attuale non corretta");
            }
            userService.updateUserPassword(user.getId(), request.getNewPassword());
            return ResponseEntity.ok("Password updated successfully");
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
    public ResponseEntity<String> deleteMyAccount(Authentication authentication ,@RequestBody Credentials credentials) {
        try {
            String email = authentication.getName();
            User user = userService.getUserByEmail(email);
            String password = credentials.getPassword();
            if (!passwordEncoder.matches(password, user.getPassword_hash())) {
                return ResponseEntity.badRequest().body("Incorrect password");
            }else{
            userService.deleteUser(user.getId());
            return ResponseEntity.ok("Account deleted!");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<User>> getLeaderboard(@RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(userService.getLeaderboard(limit));
    }

}
