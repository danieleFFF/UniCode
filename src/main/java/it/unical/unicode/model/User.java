package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private int id;
    private String username;
    private String email;
    private String password_hash;
    private int totalPoints;
    private int id_avatar;
    public User() {}
    public User(String username, String email, String password_hash, int id_avatar) {
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.id_avatar = id_avatar;
    }

    public User(int id, String username, String email, int id_avatar, int totalPoints) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.id_avatar = id_avatar;
        this.totalPoints = totalPoints;
    }

    public int getTotalPoints() { return totalPoints; }
    public int getId(){return id;}
}
