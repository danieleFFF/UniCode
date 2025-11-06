package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Esercizio {
    private int id;
    private int id_linguaggio;
    private String titolo;
    private String descrizione;
    private String difficolta;
    private String soluzione_demo;

    public Esercizio() {}
}
