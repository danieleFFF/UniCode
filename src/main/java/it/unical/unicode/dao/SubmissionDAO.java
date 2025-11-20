package it.unical.unicode.dao;

import it.unical.unicode.model.Submission;

public interface SubmissionDAO {
    boolean hasUserCompletedExercise(int idUser, int idExercise);
    void saveSubmission(Submission submission);
    Submission getSubmission(int idUser, int idExercise);
}

