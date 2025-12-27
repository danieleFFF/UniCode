package it.unical.unicode.dao;

import it.unical.unicode.model.Submission;
import java.util.List;

public interface SubmissionDAO {
    boolean hasUserCompletedExercise(int idUser, int idExercise);

    void saveSubmission(Submission submission);

    Submission getSubmission(int idUser, int idExercise);

    int countUserSubmissions(int idUser);

    List<Submission> getUserSubmissions(int idUser);
}
