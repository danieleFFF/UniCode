package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Linguaggio {
    private int id;
    private String nome;
    private String tipo_esercizi;
    private String descrizione_corso;
    private String url_video_teoria;

    public Linguaggio() {}
}
