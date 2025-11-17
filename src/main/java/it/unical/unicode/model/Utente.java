package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Utente {
    private int id;
    private String username;
    private String email;
    private String password_hash;
    private int totalPoints;
    private int id_avatar;

    public Utente() {}

    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }
}
