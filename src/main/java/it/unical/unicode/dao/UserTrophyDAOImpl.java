package it.unical.unicode.dao;

import it.unical.unicode.model.UserTrophy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserTrophyDAOImpl implements UserTrophyDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String ASSIGN_TROPHY = "INSERT INTO user_trophy (id_user, id_trophy) VALUES (?, ?)";
    private static final String  CHECK_USER_TROPHY = "SELECT COUNT(*) FROM user_trophy WHERE id_user = ? AND id_trophy = ?";
    private static final String GET_USER_TROPHY = "SELECT t.* FROM trophies t " +
            "JOIN users_trophies ut ON t.id = ut.id_trophy " +
            "WHERE ut.id_user = ?";

    public UserTrophyDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<UserTrophy> USER_TROPHY_ROW_MAPPER = new UserTrophyRowMapper();

    private static class UserTrophyRowMapper implements RowMapper<UserTrophy> {
        @Override
        public UserTrophy mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserTrophy userTrophy = new UserTrophy();
            userTrophy.setId_trophy(rs.getInt("id_trophy"));
            userTrophy.setId_user(rs.getInt("id_user"));
            return userTrophy;
        }
    }

    @Override
    public void assignTrophy(int id_user, int id_trophy) {
        jdbcTemplate.update(ASSIGN_TROPHY, id_user, id_trophy);
    }

    @Override
    public boolean hasUserTrophy(int id_user, int id_trophy) {
        Integer count = jdbcTemplate.queryForObject(CHECK_USER_TROPHY, Integer.class, id_user, id_trophy);
        return count != null && count > 0;
    }

    @Override
    public List<UserTrophy> getUserTrophy() {
        return jdbcTemplate.query(GET_USER_TROPHY, USER_TROPHY_ROW_MAPPER);
    }


}
