package it.unical.unicode.dao;

import it.unical.unicode.model.Trofeo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TrofeoDAOImpl implements TrofeoDAO {
    private final JdbcTemplate jdbcTemplate;
    public TrofeoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private class TrofeoRowMapper implements RowMapper<Trofeo> {
        @Override
        public Trofeo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trofeo trofeo = new Trofeo();
            trofeo.setId(rs.getInt("id"));
            trofeo.setNome(rs.getString("nome"));
            trofeo.setDescrizione(rs.getString("descrizione"));
            trofeo.setPunti_trofeo(rs.getInt("punti_trofeo"));
            trofeo.setCodice_trofeo(rs.getString("codice_trofeo"));
            return trofeo;
        }
    }
}
