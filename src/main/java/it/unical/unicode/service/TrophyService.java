package it.unical.unicode.service;

import it.unical.unicode.dao.*;
import it.unical.unicode.dto.TrophyDTO;
import it.unical.unicode.model.Exercise;
import it.unical.unicode.model.Submission;
import it.unical.unicode.model.Trophy;
import it.unical.unicode.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrophyService {

    private final TrophyDAO trophyDAO;
    private final UserTrophyDAO userTrophyDAO;
    private final SubmissionDAO submissionDAO;
    private final ExerciseDAO exerciseDAO;
    private final UserDAO userDAO;
    private final UserService userService;

    public TrophyService(TrophyDAO trophyDAO, UserTrophyDAO userTrophyDAO,
            SubmissionDAO submissionDAO, ExerciseDAO exerciseDAO,
            UserDAO userDAO, UserService userService) {
        this.trophyDAO = trophyDAO;
        this.userTrophyDAO = userTrophyDAO;
        this.submissionDAO = submissionDAO;
        this.exerciseDAO = exerciseDAO;
        this.userDAO = userDAO;
        this.userService = userService;
    }

    public List<TrophyDTO> getUserTrophiesWithStatus(int userId) {
        checkAndAssignTrophies(userId);

        List<Trophy> allTrophies = trophyDAO.findAllTrophy();
        List<Trophy> userUnlockedTrophies = userTrophyDAO.getUserTrophy(userId);

        List<TrophyDTO> dtos = new ArrayList<>();
        for (Trophy t : allTrophies) {
            boolean unlocked = userUnlockedTrophies.stream()
                    .anyMatch(ut -> ut.getId() == t.getId());
            dtos.add(new TrophyDTO(
                    t.getId(),
                    t.getName(),
                    t.getDescription(),
                    t.getPoints_trophy(),
                    t.getCod_trophy(),
                    unlocked));
        }
        return dtos;
    }

    public List<Trophy> checkAndAssignTrophies(int userId) {
        List<Trophy> newlyunlocked = new ArrayList<>();
        List<Submission> userSubmissions = submissionDAO.getUserSubmissions(userId);
        int completedCount = userSubmissions.size();

        checkThreshold(userId, "EXERCISE_20", completedCount >= 1, newlyunlocked);
        checkThreshold(userId, "EXERCISE_50", completedCount >= 2, newlyunlocked);
        checkThreshold(userId, "EXERCISE_100", completedCount >= 3, newlyunlocked);

        checkThreshold(userId, "CODE_MASTER", completedCount >= 5, newlyunlocked);

        List<Exercise> pythonExercises = exerciseDAO.findByLanguage(1);
        long pythonCompleted = userSubmissions.stream()
                .filter(s -> pythonExercises.stream().anyMatch(pe -> pe.getId() == s.getIdExercise()))
                .count();
        checkThreshold(userId, "PYTHON_MASTER", pythonCompleted >= 2, newlyunlocked);

        boolean allUnder10 = userSubmissions.stream().allMatch(s -> s.getTimeTakenSeconds() <= 600);
        checkThreshold(userId, "TIME_MASTER", allUnder10 && completedCount >= 5, newlyunlocked);

        List<User> ranking = userDAO.getRanking(1);
        boolean isFirst = !ranking.isEmpty() && ranking.get(0).getId() == userId;
        checkThreshold(userId, "FIRST_RANK", isFirst, newlyunlocked);

        return newlyunlocked;
    }

    private void checkThreshold(int userId, String code, boolean condition, List<Trophy> newlyUnlocked) {
        if (condition) {
            Trophy trophy = trophyDAO.findByCode(code);
            if (trophy != null && !userTrophyDAO.hasUserTrophy(userId, trophy.getId())) {
                userTrophyDAO.assignTrophy(userId, trophy.getId());
                userService.addPoints(userId, trophy.getPoints_trophy());
                newlyUnlocked.add(trophy);
            }
        }
    }
}
