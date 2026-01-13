package it.unical.unicode.controller;

import it.unical.unicode.dao.SubmissionDAO;
import it.unical.unicode.model.Submission;
import it.unical.unicode.model.Trophy;
import it.unical.unicode.service.TrophyService;
import it.unical.unicode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

//Rest controller for saving completed exercises and assign points/trophies

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {
    @Autowired
    private SubmissionDAO submissionDAO;
    @Autowired
    private TrophyService trophyService;
    @Autowired
    private UserService userService;

    @PostMapping
    public Map<String, Object> submitSolution(@RequestBody Map<String, Object> payload) {
        //Data
        int idUser = (int) payload.get("idUser");
        int idExercise = (int) payload.get("idExercise");
        int pointsEarned = (int) payload.get("pointsEarned");
        int timeTaken = (int) payload.get("timeTaken");
        String code = (String) payload.get("code");
        boolean alreadyCompleted = submissionDAO.hasUserCompletedExercise(idUser, idExercise);
        //Creates and saves current submission
        Submission submission = new Submission();
        submission.setIdUser(idUser);
        submission.setIdExercise(idExercise);
        submission.setPointsEarned(pointsEarned);
        submission.setTimeTakenSeconds(timeTaken);
        submission.setCode(code);
        submissionDAO.saveSubmission(submission);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        //Anticheat check
        if(!alreadyCompleted){
            if(pointsEarned > 0) userService.addPoints(idUser, pointsEarned);
            response.put("message", "Congratulations! You earned " + pointsEarned + " points!");
        } else response.put("message", "Congratulations! You have already earned points for this exercise.");

        //Assigns trophies and prepares response
        List<Trophy> newTrophies = trophyService.checkAndAssignTrophies(idUser);
        response.put("pointsEarned", alreadyCompleted ? 0 : pointsEarned);
        response.put("newTrophies", newTrophies);

        return response;
    }

    @GetMapping("/check/{idUser}/{idExercise}")
    public Map<String, Object> checkCompletion(@PathVariable int idUser, @PathVariable int idExercise) {
        boolean completed = submissionDAO.hasUserCompletedExercise(idUser, idExercise);
        Map<String, Object> response = new HashMap<>();
        response.put("completed", completed);

        if(completed){
            //Add points.
            Submission submission = submissionDAO.getSubmission(idUser, idExercise);
            response.put("pointsEarned", submission.getPointsEarned());
        }
        return response;
    }
}
