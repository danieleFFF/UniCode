package it.unical.unicode.model;

public class TestCase {
    private int id;
    private int id_esercizio;
    private String input;
    private String output_atteso;
    private String nome;
    private boolean passed;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getId_esercizio() { return id_esercizio; }
    public void setId_esercizio(int id_esercizio) { this.id_esercizio = id_esercizio; }
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }
    public String getOutput_atteso() { return output_atteso; }
    public void setOutput_atteso(String output_atteso) { this.output_atteso = output_atteso; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }
}
