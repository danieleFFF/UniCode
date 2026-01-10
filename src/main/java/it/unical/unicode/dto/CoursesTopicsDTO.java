package it.unical.unicode.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoursesTopicsDTO {
    private int id;
    private int idLanguage;
    private String title;
    private String introduction;
    private String codeExample;
    private String keyConcepts;
    private String urlVideo;
    private String difficulty;

    public CoursesTopicsDTO(){}
}
