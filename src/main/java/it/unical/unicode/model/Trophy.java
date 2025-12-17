package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Trophy {
    private int id;
    private String name;
    private String description;
    private int points_trophy;
    private String cod_trophy;

    public Trophy() {}
}
