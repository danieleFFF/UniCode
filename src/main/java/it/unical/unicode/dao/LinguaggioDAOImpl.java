package it.unical.unicode.dao;

import it.unical.unicode.model.Linguaggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LinguaggioDAOImpl implements LinguaggioDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class LinguaggioRowMapper implements RowMapper<Linguaggio> {
        @Override
        public Linguaggio mapRow(ResultSet rs, int rowNum) throws SQLException {
            Linguaggio linguaggio = new Linguaggio();
            linguaggio.setId(rs.getInt("id"));
            linguaggio.setNome(rs.getString("nome"));
            linguaggio.setTipo_esercizi(rs.getString("tipo_esercizi"));
            linguaggio.setDescrizione_corso(rs.getString("descrizione_corso"));
            linguaggio.setUrl_video_teoria(rs.getString("url_video_teoria"));
            return linguaggio;
        }
    }
}
