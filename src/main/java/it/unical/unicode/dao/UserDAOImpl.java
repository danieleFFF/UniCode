package it.unical.unicode.dao;

import it.unical.unicode.model.User;
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
public class UserDAOImpl implements UserDAO {
    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class UtenteRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword_hash(rs.getString("password_hash"));
            user.setTotalPoints(rs.getInt("total_points"));
            user.setId_avatar(rs.getInt("id_avatar"));
            return user;
        }
    }
    //Prova
    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (username, email, password_hash, total_points, id_avatar) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(),
                user.getEmail(),
                user.getPassword_hash(),
                0,//Default starting points
                1 //Default Avatar
        );
    }

    @Override
    public Optional<User> findByEmail(String email) {
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
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), id);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void updateTotalPoints(int userId, int pointsToAdd) {
        String sql = "UPDATE users SET total_points = total_points + ? WHERE id = ?";
        jdbcTemplate.update(sql, pointsToAdd, userId);
    }

    @Override
    public List<User> getRanking(int limit) {
        String sql = "SELECT * FROM users ORDER BY total_points DESC LIMIT ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), limit);
    }

    public void updateAvatar(int userId, int avatarId){
        String query = "UPDATE users SET id_avatar = ? WHERE id = ?";
        jdbcTemplate.update(query, avatarId, userId);
    }

}
