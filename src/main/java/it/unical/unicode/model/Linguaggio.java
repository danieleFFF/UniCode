package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Linguaggio {
    private int id;
    private String nome;
    private String tipo_esercizi;

    public Linguaggio() {
    }
}
