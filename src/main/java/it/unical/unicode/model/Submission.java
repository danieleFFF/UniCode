package it.unical.unicode.model;

import java.time.LocalDateTime;

public class Submission {
    private int id;
    private int idUser;
    private int idExercise;
    private int pointsEarned;
    private int timeTakenSeconds;
    private LocalDateTime completedAt;
    private String code;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public int getIdExercise() { return idExercise; }
    public void setIdExercise(int idExercise) { this.idExercise = idExercise; }
    public int getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(int pointsEarned) { this.pointsEarned = pointsEarned; }
    public int getTimeTakenSeconds() { return timeTakenSeconds; }
    public void setTimeTakenSeconds(int timeTakenSeconds) { this.timeTakenSeconds = timeTakenSeconds; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
