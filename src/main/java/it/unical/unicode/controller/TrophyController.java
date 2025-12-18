package it.unical.unicode.controller;

import it.unical.unicode.dto.TrophyDTO;
import it.unical.unicode.model.Trophy;
import it.unical.unicode.service.TrophyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trophies")
public class TrophyController {

    private final TrophyService trophyService;

    public TrophyController(TrophyService trophyService) {
        this.trophyService = trophyService;
    }

    @GetMapping("/user/{userId}")
    public List<TrophyDTO> getUserTrophies(@PathVariable int userId) {
        return trophyService.getUserTrophiesWithStatus(userId);
    }

    @PostMapping("/check/{userId}")
    public List<Trophy> checkTrophies(@PathVariable int userId) {
        return trophyService.checkAndAssignTrophies(userId);
    }
}