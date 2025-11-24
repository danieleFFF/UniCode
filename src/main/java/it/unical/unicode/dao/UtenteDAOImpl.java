package it.unical.unicode.dao;

import it.unical.unicode.model.Utente;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
            utente.setTotalPoints(rs.getInt("total_points"));
            utente.setId_avatar(rs.getInt("id_avatar"));
            return utente;
        }
    }
    //Prova
    @Override
    public void save(Utente utente) {
        String sql = "INSERT INTO users (username, email, password_hash, total_points, id_avatar) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, utente.getUsername(),
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

    @Override
    public void resetPassword(String email, String newPassword) {
        String sql = "UPDATE users SET password_hash = ? WHERE email = ?";
        jdbcTemplate.update(sql, newPassword, email);
    }

    @Override
    public Utente findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<Utente> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Utente.class), id);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<Utente> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Utente.class));
    }

    @Override
    public void updateTotalPoints(int userId, int pointsToAdd) {
        String sql = "UPDATE users SET total_points = total_points + ? WHERE id = ?";
        jdbcTemplate.update(sql, pointsToAdd, userId);
    }

    @Override
    public List<Utente> getRanking(int limit) {
        String sql = "SELECT * FROM users ORDER BY total_points DESC LIMIT ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Utente.class), limit);
    }

    public void updateAvatar(int userId, int avatarId){
        String query = "UPDATE users SET id_avatar = ? WHERE id = ?";
        jdbcTemplate.update(query, avatarId, userId);
    }

}
