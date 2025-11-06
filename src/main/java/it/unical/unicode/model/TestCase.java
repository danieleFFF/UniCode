package it.unical.unicode.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestCase {
    private int id;
    private int id_esercizio;
    private String input;
    private String output_atteso;

    public TestCase() {}
}
