package it.unical.unicode.service;

import it.unical.unicode.dao.UserDAO;
import it.unical.unicode.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO , PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(int id){
       return userDAO.findById(id).orElseThrow(() -> new RuntimeException("User not found by id"));
    }

    public User getUserByEmail(String email){
        return userDAO.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found by email"));
    }

    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

    public void deleteUser(int id){
        if(userDAO.findById(id).isPresent()){
            userDAO.deleteUser(id);
        }else{
            throw new RuntimeException("impossibile eliminare l'utente selezionato , utente non trovato");
        }
    }

    public void updateUserPassword(int id , String newPassword){

        if(userDAO.findById(id).isPresent()){
            String newHashPassword = passwordEncoder.encode(newPassword);
            userDAO.updatePassword(newHashPassword,id);
        }else{
            throw new RuntimeException("impossibile aggiornare la password , utente non trovato");
        }
    }

    public void updateUserAvatar(int id , int avatarId){
        if(userDAO.findById(id).isPresent()){
            userDAO.updateAvatar(id,avatarId);
        }else {
            throw new RuntimeException("impossibile aggiornare l'avatar , utente non trovato");
        }
    }

    public List<User> getLeaderboard(int limit) {
        return userDAO.getRanking(limit);
    }

    public void addPoints(int userId, int points) {
        if (userDAO.findById(userId).isPresent()) {
            userDAO.updateTotalPoints(userId, points);
        } else {
            throw new RuntimeException("Impossibile assegnare punti: Utente non trovato");
        }
    }

}
