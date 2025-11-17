package it.unical.unicode.dao;

import it.unical.unicode.model.Submission;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SubmissionDAOImpl implements SubmissionDAO {
    private final JdbcTemplate jdbcTemplate;

    public SubmissionDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean hasUserCompletedExercise(int idUser, int idExercise) {
        String sql = "SELECT COUNT(*) FROM submissions WHERE id_user = ? AND id_exercise = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUser, idExercise);
        return count != null && count > 0;
    }

    @Override
    public void saveSubmission(Submission submission) {
        String sql = "INSERT INTO submissions (id_user, id_exercise, points_earned, time_taken_seconds, code) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (id_user, id_exercise) DO NOTHING";

        jdbcTemplate.update(sql,
                submission.getIdUser(),
                submission.getIdExercise(),
                submission.getPointsEarned(),
                submission.getTimeTakenSeconds(),
                submission.getCode()
        );
    }

    @Override
    public Submission getSubmission(int idUser, int idExercise) {
        String sql = "SELECT * FROM submissions WHERE id_user = ? AND id_exercise = ?";
        List<Submission> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Submission.class), idUser, idExercise);
        return results.isEmpty() ? null : results.get(0);
    }
}