package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EsercizioSvolto {
    private int id_utente;
    private int id_esercizio;
    private int tempo_impiegato_sec;
    private int punti_ottenuti;
    private boolean is_first_try;

    public EsercizioSvolto() {}
}
