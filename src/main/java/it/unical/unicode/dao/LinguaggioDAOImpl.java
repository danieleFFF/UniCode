package it.unical.unicode.dao;

import it.unical.unicode.model.Linguaggio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LinguaggioDAOImpl implements LinguaggioDAO {
    private final JdbcTemplate jdbcTemplate;

    public LinguaggioDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private class LinguaggioRowMapper implements RowMapper<Linguaggio> {
        @Override
        public Linguaggio mapRow(ResultSet rs, int rowNum) throws SQLException {
            Linguaggio linguaggio = new Linguaggio();
            linguaggio.setId(rs.getInt("id"));
            linguaggio.setNome(rs.getString("nome"));
            linguaggio.setTipo_esercizi(rs.getString("tipo_esercizi"));
            return linguaggio;
        }
    }
}
