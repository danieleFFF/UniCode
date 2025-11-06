package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Trofeo {
    private int id;
    private String nome;
    private String descrizione;
    private int punti_trofeo;
    private String codice_trofeo;

    public Trofeo() {}
}
