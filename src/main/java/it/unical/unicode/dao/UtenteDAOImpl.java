package it.unical.unicode.dao;

import it.unical.unicode.model.Utente;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UtenteDAOImpl implements UtenteDAO {
    private final JdbcTemplate jdbcTemplate;

    public UtenteDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class UtenteRowMapper implements RowMapper<Utente> {
        @Override
        public Utente mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utente utente = new Utente();
            utente.setId(rs.getInt("id"));
            utente.setUsername(rs.getString("username"));
            utente.setEmail(rs.getString("email"));
            utente.setPassword_hash(rs.getString("password_hash"));
            utente.setPunti_totali(rs.getInt("punti_totali"));
            utente.setId_avatar(rs.getInt("id_avatar"));
            return utente;
        }
    }

    @Override
    public Utente findByEmail(String email){
        String sql="SELECT * FROM utenti WHERE email = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new UtenteRowMapper(), email);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
