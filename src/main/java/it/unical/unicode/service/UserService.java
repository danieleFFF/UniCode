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

    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder){
        this.userDAO=userDAO;
        this.passwordEncoder=passwordEncoder;
    }

    public User getUserByEmail(String email){
        User user=userDAO.findByEmail(email);
        if(user==null){
            throw new RuntimeException("User not found by email");
        }
        return user;
    }

    public void deleteUser(int id){
        User user=userDAO.findById(id);
        if(user!=null){
            userDAO.deleteUser(id);
        }
        else{
            throw new RuntimeException("Unable to delete the selected user: user not found.");
        }
    }

    public void updateUserPassword(int id, String newPassword){
        User user=userDAO.findById(id);
        if(user!=null){
            String newHashPassword=passwordEncoder.encode(newPassword);
            userDAO.updatePassword(newHashPassword,id);
        }
        else{
            throw new RuntimeException("Unable to update password: user not found");
        }
    }

    public void updateUserAvatar(int id , int avatarId){
        User user=userDAO.findById(id);
        if(user!=null){
            userDAO.updateAvatar(id,avatarId);
        }
        else{
            throw new RuntimeException("Unable to update avatar: user not found");
        }
    }

    public List<User> getLeaderboard(int limit) {
        return userDAO.getRanking(limit);
    }

    public void addPoints(int userId, int points){
        User user=userDAO.findById(userId);
        if(user!=null){
            userDAO.updateTotalPoints(userId,points);
        }
        else{
            throw new RuntimeException("Unable to assign points: user not found.");
        }
    }

    public List<User> getNonAdminUsers(){
        return userDAO.getNonAdminUsers();
    }

    public void promoteToAdmin(int userId){
        User user=userDAO.findById(userId);
        if(user!=null){
            userDAO.makeUserAdmin(userId);
        }
        else{
            throw new RuntimeException("User not found with id: "+userId);
        }
    }

    public void banUser(int userId){
        User user=userDAO.findById(userId);
        if(user!=null){
            userDAO.banUser(userId);
        }
        else{
            throw new RuntimeException("User not found with id: "+userId);
        }
    }

    public void unbanUser(int userId){
        User user=userDAO.findById(userId);
        if(user!=null){
            userDAO.unbanUser(userId);
        }
        else{
            throw new RuntimeException("User not found with id: "+userId);
        }
    }
}