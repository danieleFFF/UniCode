package it.unical.unicode.dao;

import it.unical.unicode.model.Submission;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SubmissionDAOImpl implements SubmissionDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String SAVE_SUBMISSION = "INSERT INTO submissions (id_user, id_exercise, points_earned, time_taken_seconds, code) "
            +
            "VALUES (?, ?, ?, ?, ?) " +
            "ON CONFLICT (id_user, id_exercise) DO UPDATE SET " +
            "points_earned = EXCLUDED.points_earned, " +
            "time_taken_seconds = EXCLUDED.time_taken_seconds, " +
            "code = EXCLUDED.code, " +
            "completed_at = CURRENT_TIMESTAMP";
    private static final String GET_SUBMISSION = "SELECT * FROM submissions WHERE id_user = ? AND id_exercise = ?";
    private static final String COMPLETED_EXCERSIZE = "SELECT COUNT(*) FROM submissions WHERE id_user = ? AND id_exercise = ?";
    private static final String COUNT_USER_SUBMISSIONS = "SELECT COUNT(*) FROM submissions WHERE id_user = ?";

    public SubmissionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean hasUserCompletedExercise(int idUser, int idExercise) {
        Integer count = jdbcTemplate.queryForObject(COMPLETED_EXCERSIZE, Integer.class, idUser, idExercise);
        return count != null && count > 0;
    }

    @Override
    public void saveSubmission(Submission submission) {

        jdbcTemplate.update(SAVE_SUBMISSION,
                submission.getIdUser(),
                submission.getIdExercise(),
                submission.getPointsEarned(),
                submission.getTimeTakenSeconds(),
                submission.getCode());
    }

    @Override
    public Submission getSubmission(int idUser, int idExercise) {
        List<Submission> results = jdbcTemplate.query(GET_SUBMISSION, new BeanPropertyRowMapper<>(Submission.class),
                idUser, idExercise);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public int countUserSubmissions(int idUser) {
        Integer count = jdbcTemplate.queryForObject(COUNT_USER_SUBMISSIONS, Integer.class, idUser);
        return count != null ? count : 0;
    }

    @Override
    public List<Submission> getUserSubmissions(int idUser) {
        return jdbcTemplate.query("SELECT * FROM submissions WHERE id_user = ?", new BeanPropertyRowMapper<>(Submission.class), idUser);
    }
}