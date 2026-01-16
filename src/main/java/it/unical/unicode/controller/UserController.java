package it.unical.unicode.controller;

import it.unical.unicode.dto.ChangePasswordDTO;
import it.unical.unicode.dto.Credentials;
import it.unical.unicode.dto.RegisterRequest;
import it.unical.unicode.dto.UserDTO;
import it.unical.unicode.model.User;
import it.unical.unicode.service.RegisterService;
import it.unical.unicode.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
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

    private User getCurrentUser(Authentication auth) {
        return userService.getUserByEmail(auth.getName());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            registerService.registerUser(registerRequest);
            return ResponseEntity.ok("User successfully registered.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile(Authentication authentication) {
        try {
            User user = getCurrentUser(authentication);

            UserDTO dto = UserDTO.toDTO(user);

            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO request, Authentication authentication) {
        try {
            User user = getCurrentUser(authentication);

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword_hash())) {
                return ResponseEntity.badRequest().body("Current password is incorrect");
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
            User user = getCurrentUser(authentication);

            userService.updateUserAvatar(user.getId(), avatarId);
            return ResponseEntity.ok("Avatar updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMyAccount(Authentication authentication ,@RequestBody Credentials credentials) {
        try {
            User user = getCurrentUser(authentication);
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
    public ResponseEntity<List<UserDTO>> getLeaderboard(@RequestParam(defaultValue = "10") int limit) {
        List<User> users = userService.getLeaderboard(limit);

        List<UserDTO> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(UserDTO.toDTO(user));
        }

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/nonadmin-users")
    public ResponseEntity<?> getNonAdminUsers(Authentication authentication){
        try{
            User user=getCurrentUser(authentication);
            if(!user.isAdmin()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
            }
            List<User> users=userService.getNonAdminUsers();
            List<UserDTO> dtos=new ArrayList<>();
            for(User u:users){
                dtos.add(UserDTO.toDTO(u));
            }
            return ResponseEntity.ok(dtos);
        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @PutMapping("/make-admin")
    public ResponseEntity<String> makeAdmin(@RequestParam int userId, Authentication authentication){
        try{
            User requester=getCurrentUser(authentication);
            if(!requester.isAdmin()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
            }
            userService.promoteToAdmin(userId);
            return ResponseEntity.ok("User promoted to admin.");
        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @PutMapping("/ban-user")
    public ResponseEntity<String> banUser(@RequestParam int userId, Authentication authentication){
        try{
            User requester=getCurrentUser(authentication);
            if(!requester.isAdmin()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
            }
            userService.banUser(userId);
            return ResponseEntity.ok("User has been banned.");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }

    @PutMapping("/unban-user")
    public ResponseEntity<String> unbanUser(@RequestParam int userId, Authentication authentication){
        try{
            User requester=getCurrentUser(authentication);
            if(!requester.isAdmin()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
            }
            userService.unbanUser(userId);
            return ResponseEntity.ok("User has been unbanned.");
        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body("Error: "+e.getMessage());
        }
    }
}
