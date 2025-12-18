package it.unical.unicode.service;

import it.unical.unicode.dao.SubmissionDAO;
import it.unical.unicode.dao.TrophyDAO;
import it.unical.unicode.dao.UserDAO;
import it.unical.unicode.dao.UserTrophyDAO;
import it.unical.unicode.dto.TrophyDTO;
import it.unical.unicode.model.Trophy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrophyService {

    private final TrophyDAO trophyDAO;
    private final SubmissionDAO submissionDAO;
    private final UserDAO userDAO;
    private final UserTrophyDAO userTrophyDAO;

    public TrophyService(TrophyDAO trophyDAO, SubmissionDAO submissionDAO, UserDAO userDAO,
            UserTrophyDAO userTrophyDAO) {
        this.trophyDAO = trophyDAO;
        this.submissionDAO = submissionDAO;
        this.userDAO = userDAO;
        this.userTrophyDAO = userTrophyDAO;
    }

    private void assignIfNew(int userId, String codTrophy, List<Trophy> newTrophies) {
        Trophy trophy = trophyDAO.findByCode(codTrophy);
        if (trophy != null && !userTrophyDAO.hasUserTrophy(userId, trophy.getId())) {
            userTrophyDAO.assignTrophy(userId, trophy.getId());
            newTrophies.add(trophy);
        }
    }

    private List<Trophy> checkExerciseCountTrophies(int userId) {
        List<Trophy> newTrophies = new ArrayList<>();
        int completedCount = submissionDAO.countUserSubmissions(userId);

        if (completedCount >= 20) {
            assignIfNew(userId, "EXERCISE_20", newTrophies);
        }
        if (completedCount >= 50) {
            assignIfNew(userId, "EXERCISE_50", newTrophies);
        }
        if (completedCount >= 100) {
            assignIfNew(userId, "EXERCISE_100", newTrophies);
        }
        return newTrophies;
    }

    public List<Trophy> checkAndAssignTrophies(int userId) {
        List<Trophy> newTrophies = new ArrayList<>();

        List<Trophy> exerciseTrophies = checkExerciseCountTrophies(userId);
        newTrophies.addAll(exerciseTrophies);

        for (Trophy trophy : newTrophies) {
            userDAO.updateTotalPoints(userId, trophy.getPoints_trophy());
        }

        return newTrophies;
    }

    public List<TrophyDTO> getUserTrophiesWithStatus(int userId) {
        List<Trophy> allTrophies = trophyDAO.findAllTrophy();

        List<Trophy> userTrophies = userTrophyDAO.getUserTrophy(userId);

        List<TrophyDTO> result = new ArrayList<>();

        for (Trophy trophy : allTrophies) {
            boolean unlocked = false;
            for (Trophy userTrophy : userTrophies) {
                if (userTrophy.getId() == trophy.getId()) {
                    unlocked = true;
                    break;
                }
            }

            TrophyDTO dto = new TrophyDTO(
                    trophy.getId(),
                    trophy.getName(),
                    trophy.getDescription(),
                    trophy.getPoints_trophy(),
                    trophy.getCod_trophy(),
                    unlocked);
            result.add(dto);
        }

        return result;
    }

}
