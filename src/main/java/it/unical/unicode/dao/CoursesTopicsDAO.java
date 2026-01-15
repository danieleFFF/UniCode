package it.unical.unicode.dao;

import java.util.List;
import it.unical.unicode.dto.CoursesTopicsDTO;

public interface CoursesTopicsDAO {
    List<CoursesTopicsDTO> getTopicsByLanguageId(int languageId);
}
