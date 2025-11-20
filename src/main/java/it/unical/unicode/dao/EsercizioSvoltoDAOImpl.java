package it.unical.unicode.dao;

import it.unical.unicode.model.EsercizioSvolto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class EsercizioSvoltoDAOImpl implements EsercizioSvoltoDAO {
    private final JdbcTemplate jdbcTemplate;
    public EsercizioSvoltoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private class EsercizioSvoltoRowMapper implements RowMapper<EsercizioSvolto> {
        @Override
        public EsercizioSvolto mapRow(ResultSet rs, int rowNum) throws SQLException {
            EsercizioSvolto esercizio_svolto = new EsercizioSvolto();
            esercizio_svolto.setId_esercizio(rs.getInt("id_esercizio"));
            esercizio_svolto.set_first_try(rs.getBoolean("first_try"));
            esercizio_svolto.setId_utente(rs.getInt("id_utente"));
            esercizio_svolto.setPunti_ottenuti(rs.getInt("punti_ottenuti"));
            esercizio_svolto.setTempo_impiegato_sec(rs.getInt("tempo_impiegato_sec"));
            return esercizio_svolto;
        }
    }
}
