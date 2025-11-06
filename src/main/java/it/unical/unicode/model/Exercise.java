package it.unical.unicode.model;

public class Exercise {
    private int id;
    private String title;
    private String description;
    private String difficulty;
    private String language;
    private int points;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
