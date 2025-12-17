package it.unical.unicode.service;

import it.unical.unicode.dao.SubmissionDAO;
import it.unical.unicode.dao.TrophyDAO;
import it.unical.unicode.dao.UserDAO;
import it.unical.unicode.dao.UserTrophyDAO;
import it.unical.unicode.model.Trophy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrophyService {

    private final TrophyDAO trophyDAO;
    private final SubmissionDAO submissionDAO;
    private final UserDAO userDAO;
    private final UserTrophyDAO userTrophyDAO;

    public TrophyService(TrophyDAO trophyDAO, SubmissionDAO submissionDAO, UserDAO userDAO, UserTrophyDAO userTrophyDAO) {
        this.trophyDAO = trophyDAO;
        this.submissionDAO = submissionDAO;
        this.userDAO = userDAO;
        this.userTrophyDAO = userTrophyDAO;
    }

    private void assegnIfNew(int userId , String codTrophy , List<Trophy> newTrophies){
        Trophy trophy = trophyDAO.findByCode(codTrophy);
        if(trophy != null && userTrophyDAO.hasUserTrophy(userId,trophy.getId())){
            userTrophyDAO.assignTrophy(userId,trophy.getId());
            newTrophies.add(trophy);
        }
    }

}
