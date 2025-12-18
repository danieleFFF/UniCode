package it.unical.unicode.controller;

import it.unical.unicode.dao.SubmissionDAO;
import it.unical.unicode.model.Submission;
import it.unical.unicode.model.Trophy;
import it.unical.unicode.service.TrophyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    @Autowired
    private SubmissionDAO submissionDAO;
    @Autowired
    private TrophyService trophyService;

    @PostMapping
    public Map<String, Object> submitSolution(@RequestBody Map<String, Object> payload) {
        int idUser = (int) payload.get("idUser");
        int idExercise = (int) payload.get("idExercise");
        int pointsEarned = (int) payload.get("pointsEarned");
        int timeTaken = (int) payload.get("timeTaken");
        String code = (String) payload.get("code");

        boolean alreadyCompleted = submissionDAO.hasUserCompletedExercise(idUser, idExercise);

        if (alreadyCompleted) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message",  "You have already completed this exercise");
            response.put("pointsEarned",0);
            return response;
        }

        Submission submission = new Submission();
        submission.setIdUser(idUser);
        submission.setIdExercise(idExercise);
        submission.setPointsEarned(pointsEarned);
        submission.setTimeTakenSeconds(timeTaken);
        submission.setCode(code);
        submissionDAO.saveSubmission(submission);
        List<Trophy> newTrophies = trophyService.checkAndAssignTrophies(idUser);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Congratulations! You earned " + pointsEarned + " points!");
        response.put("pointsEarned", pointsEarned);
        response.put("newTrophies", newTrophies);
        return response;
    }

    @GetMapping("/check/{idUser}/{idExercise}")
    public Map<String, Object> checkCompletion(@PathVariable int idUser, @PathVariable int idExercise) {
        boolean completed = submissionDAO.hasUserCompletedExercise(idUser, idExercise);
        Map<String, Object> response = new HashMap<>();
        response.put("completed", completed);

        if (completed){
            Submission submission = submissionDAO.getSubmission(idUser, idExercise);
            response.put("pointsEarned", submission.getPointsEarned());
        }
        return response;
    }
}
