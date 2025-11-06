package it.unical.unicode.dao;

import it.unical.unicode.model.Esercizio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class EsercizioDAOImpl implements EsercizioDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class EsercizioRowMapper implements RowMapper<Esercizio> {
        @Override
        public Esercizio mapRow(ResultSet rs, int rowNum) throws SQLException {
            Esercizio esercizio = new Esercizio();
            esercizio.setId(rs.getInt("id"));
            esercizio.setDescrizione(rs.getString("descrizione"));
            esercizio.setDifficolta(rs.getString("difficolta"));
            esercizio.setTitolo(rs.getString("titolo"));
            esercizio.setId_linguaggio(rs.getInt("id_linguaggio"));
            esercizio.setSoluzione_demo(rs.getString("soluzione_demo"));
            return esercizio;
        }
    }
}
