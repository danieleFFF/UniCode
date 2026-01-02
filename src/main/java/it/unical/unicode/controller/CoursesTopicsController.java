package it.unical.unicode.controller;

import it.unical.unicode.dao.CoursesTopicsDAO;
import it.unical.unicode.dto.CoursesTopicsDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200")
public class CoursesTopicsController {
    private final CoursesTopicsDAO coursesTopicsDAO;

    public CoursesTopicsController(CoursesTopicsDAO coursesTopicsDAO){
        this.coursesTopicsDAO=coursesTopicsDAO;
    }

    @GetMapping("/language/{languageId}")
    public List<CoursesTopicsDTO> getTopicsByLanguage(@PathVariable int languageId){
        return coursesTopicsDAO.getTopicsByLanguageId(languageId);
    }
}
