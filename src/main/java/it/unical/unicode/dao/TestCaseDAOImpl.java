package it.unical.unicode.dao;

import it.unical.unicode.model.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TestCaseDAOImpl implements TestCaseDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class TestCaseRowMapper implements RowMapper<TestCase> {
        @Override
        public TestCase mapRow(ResultSet rs, int rowNum) throws SQLException {
            TestCase test_case = new TestCase();
            test_case.setId(rs.getInt("id"));
            test_case.setInput(rs.getString("input"));
            test_case.setId_exercise(rs.getInt("id_exercise"));
            test_case.setExpected_output(rs.getString("expected_output"));
            return test_case;
        }
    }
}