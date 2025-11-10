package it.unical.unicode.dao;

import it.unical.unicode.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UtenteDAOImpl implements UtenteDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class UtenteRowMapper implements RowMapper<Utente> {
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
    public void save(Utente utente) {
        String sql = "INSERT INTO users (id,username, email, password_hash, punti_totali, id_avatar) VALUES (?,?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,utente.getId(),
                utente.getUsername(),
                utente.getEmail(),
                utente.getPassword_hash(),
                0,//Default starting points
                1 //Default Avatar
        );
    }

    @Override
    public Optional<Utente> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UtenteRowMapper(), email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
