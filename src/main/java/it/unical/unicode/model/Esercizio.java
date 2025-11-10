package it.unical.unicode.model;

public class Esercizio {
    private int id;
    private int id_language;
    private String title;
    private String description;
    private String difficulty;
    private String solution_demo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getId_language() { return id_language; }
    public void setId_language(int id_language) { this.id_language = id_language; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getSolution_demo() { return solution_demo; }
    public void setSolution_demo(String solution_demo) { this.solution_demo = solution_demo; }

    public int getPoints() {
        return switch (difficulty) {
            case "Easy" -> 50;
            case "Medium" -> 100;
            case "Hard" -> 150;
            default -> 0;
        };
    }
}
