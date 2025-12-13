package it.unical.unicode.dto;

public class UserDTO {
    private int id;
    private String username;
    private String email;
    private int id_avatar;
    private int total_points;

    public UserDTO() {}

    public UserDTO(int id, String username, String email, int id_avatar, int total_points) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.id_avatar = id_avatar;
        this.total_points = total_points;
    }

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public int getId_avatar() { return id_avatar; }
    public int getPunti() { return total_points; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setId_avatar(int id_avatar) { this.id_avatar = id_avatar; }
    public void setPunti(int total_points) { this.total_points = total_points; }
}
