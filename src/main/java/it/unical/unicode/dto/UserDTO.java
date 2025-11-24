package it.unical.unicode.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private int id_avatar;
    private int punti;
}
