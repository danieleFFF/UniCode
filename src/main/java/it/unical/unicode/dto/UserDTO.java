package it.unical.unicode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unical.unicode.model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private int id_avatar;
    private int total_points;
    private boolean isAdmin;

    @JsonProperty("isBanned") //serve per forzare il nome del campo nel JSON ad essere isBanned, altrimenti era banned e dava problemi
    private boolean isBanned;

    public UserDTO(int id, String username, String email, int id_avatar, int total_points, boolean isAdmin, boolean isBanned){
        this.id=id;
        this.username=username;
        this.email=email;
        this.id_avatar=id_avatar;
        this.total_points=total_points;
        this.isAdmin=isAdmin;
        this.isBanned=isBanned;
    }

    public static UserDTO toDTO(User user){
        return new UserDTO(user.getId(),user.getUsername(),user.getEmail(),user.getId_avatar(),user.getTotal_points(),user.isAdmin(),user.isBanned()
        );
    }
}
