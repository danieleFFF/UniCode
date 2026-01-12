package it.unical.unicode.dao;

import it.unical.unicode.model.TestCase;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TestCaseDAOImpl implements TestCaseDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_TEST_CASE = "INSERT INTO test_cases (id_exercise, input, expected_output) VALUES (?, ?, ?)";
    private static final String DELETE_TEST_CASE = "DELETE FROM test_cases WHERE id_exercise = ?";

    public TestCaseDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(List<TestCase> tests, int exerciseId){
        jdbcTemplate.batchUpdate(INSERT_TEST_CASE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TestCase test = tests.get(i);
                ps.setInt(1, exerciseId);
                ps.setString(2, test.getInput());
                ps.setString(3, test.getExpected_output());
            }

            @Override
            public int getBatchSize() {
                return tests.size();
            }
        });
    }

    @Override
    public void deleteByExerciseId(int exerciseId) {
        jdbcTemplate.update(DELETE_TEST_CASE, exerciseId);
    }
}
