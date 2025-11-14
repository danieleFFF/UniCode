package it.unical.unicode.dao;

import it.unical.unicode.model.TrofeoUtente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TrofeoUtenteDAOImpl implements TrofeoUtenteDAO {
    private final JdbcTemplate jdbcTemplate;
    public TrofeoUtenteDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private class TrofeoUtenteRowMapper implements RowMapper<TrofeoUtente> {
        @Override
        public TrofeoUtente mapRow(ResultSet rs, int rowNum) throws SQLException {
            TrofeoUtente trofeo_utente = new TrofeoUtente();
            trofeo_utente.setId_trofeo(rs.getInt("id_trofeo"));
            trofeo_utente.setId_utente(rs.getInt("id_utente"));
            return trofeo_utente;
        }
    }
}
