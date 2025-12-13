package it.unical.unicode.dao;

import it.unical.unicode.model.Avatar;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AvatarDAOImpl implements AvatarDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_AVATARS = "SELECT * FROM avatar"; // query per recuperare tutti gli avatar (sia id che percorso nel progetto )

    public AvatarDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private class AvatarRowMapper implements RowMapper<Avatar> {
        @Override
        public Avatar mapRow(ResultSet rs, int rowNum) throws SQLException {
            Avatar avatar = new Avatar();
            avatar.setId(rs.getInt("id"));
            avatar.setUrl_immagine(rs.getString("url_image"));
            return avatar;
        }
    }
    @Override
    public List<Avatar> getAllAvatars() {
        return jdbcTemplate.query(GET_ALL_AVATARS, new AvatarRowMapper());
    }

}
