package it.unical.unicode.dto;


public class TrophyDTO {

    private int id;
    private String name;
    private String description;
    private int points_trophy;
    private String cod_trophy;
    private boolean unlocked;
    public TrophyDTO() {}

    public TrophyDTO(int id, String name, String description, int points_trophy, String cod_trophy, boolean unlocked) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.points_trophy = points_trophy;
        this.cod_trophy = cod_trophy;
        this.unlocked = unlocked;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPoints_trophy() { return points_trophy; }
    public String getCod_trophy() { return cod_trophy; }
    public boolean isUnlocked() { return unlocked; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPoints_trophy(int points_trophy) { this.points_trophy = points_trophy; }
    public void setCod_trophy(String cod_trophy) { this.cod_trophy = cod_trophy; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }


}
