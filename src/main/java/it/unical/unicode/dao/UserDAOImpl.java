package it.unical.unicode.dao;

import it.unical.unicode.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_USER = "INSERT INTO users (username, email, password_hash, total_points, id_avatar) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_PASSWORD = "UPDATE users SET password_hash = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String FIND_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?"; // Added this line
    private static final String RESET_PASSWORD = "UPDATE users SET password_hash = ? WHERE email = ?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM users";
    private static final String UPDATE_TOT_POINTS = "UPDATE users SET total_points = total_points + ? WHERE id = ?";
    private static final String UPDATE_AVATAR = "UPDATE users SET id_avatar = ? WHERE id = ?";
    private static final String GET_RANKING = "SELECT * FROM users ORDER BY total_points DESC LIMIT ?";

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<User> USER_MAP = new UserRowMapper();

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword_hash(rs.getString("password_hash"));
            user.setTotal_points(rs.getInt("total_points"));
            user.setId_avatar(rs.getInt("id_avatar"));
            return user;
        }
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update(INSERT_USER, user.getUsername(),
                user.getEmail(),
                user.getPassword_hash(),
                0, // Default starting points
                1 // Default Avatar
        );
    }

    @Override
    public User findByEmail(String email) {
        List<User> users = jdbcTemplate.query(FIND_USER_BY_EMAIL, USER_MAP, email);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = jdbcTemplate.query(FIND_USER_BY_USERNAME, USER_MAP, username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        jdbcTemplate.update(RESET_PASSWORD, newPassword, email);
    }

    @Override
    public User findById(int id) {
        List<User> users = jdbcTemplate.query(FIND_USER_BY_ID, USER_MAP, id);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL, USER_MAP);
    }

    @Override
    public void updateTotalPoints(int userId, int pointsToAdd) {
        jdbcTemplate.update(UPDATE_TOT_POINTS, pointsToAdd, userId);
    }

    @Override
    public List<User> getRanking(int limit) {
        return jdbcTemplate.query(GET_RANKING, USER_MAP, limit);
    }

    @Override
    public void updateAvatar(int userId, int avatarId) {
        jdbcTemplate.update(UPDATE_AVATAR, avatarId, userId);
    }

    @Override
    public void deleteUser(int id) {
        jdbcTemplate.update(DELETE_USER, id);
    }

    @Override
    public void updatePassword(String newPassword, int id) {
        jdbcTemplate.update(UPDATE_USER_PASSWORD, newPassword, id);
    }

}
