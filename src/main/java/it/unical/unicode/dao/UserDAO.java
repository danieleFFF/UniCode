package it.unical.unicode.dao;

import it.unical.unicode.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username); // Added this line
    void resetPassword(String email, String newPassword);
    Optional<User> findById(int id);
    List<User> findAll();
    void updateTotalPoints(int userId, int pointsToAdd);
    List<User> getRanking(int limit);
    void updateAvatar(int userId, int avatarId);
    void deleteUser(int id);
    void updatePassword(String newPassword, int id);
}
