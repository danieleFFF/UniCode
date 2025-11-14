package it.unical.unicode.dao;

import it.unical.unicode.model.TestCase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TestCaseDAOImpl implements TestCaseDAO {
    private final JdbcTemplate jdbcTemplate;
    public TestCaseDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private class TestCaseRowMapper implements RowMapper<TestCase> {
        @Override
        public TestCase mapRow(ResultSet rs, int rowNum) throws SQLException {
            TestCase test_case = new TestCase();
            test_case.setId(rs.getInt("id"));
            test_case.setInput(rs.getString("input"));
            test_case.setId_esercizio(rs.getInt("id_esercizio"));
            test_case.setOutput_atteso(rs.getString("output_atteso"));
            return test_case;
        }
    }
}
