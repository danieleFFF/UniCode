package it.unical.unicode.dto;


public class AvatarUpdateDTO {
    private int avatarId;
    private String url;

    public AvatarUpdateDTO() {}

    public AvatarUpdateDTO(int avatarId, String url) {
        this.avatarId = avatarId;
        this.url = url;
    }

    public int getAvatarId() { return avatarId; }
    public String getUrl() { return url; }
    public void setAvatarId(int avatarId) { this.avatarId = avatarId; }
    public void setUrl(String url) { this.url = url; }
}
