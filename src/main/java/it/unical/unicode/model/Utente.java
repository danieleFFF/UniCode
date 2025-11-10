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
    private int punti_totali;
    private int id_avatar;

    public Utente() {}
}