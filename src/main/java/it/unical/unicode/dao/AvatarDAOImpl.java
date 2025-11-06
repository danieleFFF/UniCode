package it.unical.unicode.dao;

import it.unical.unicode.model.Avatar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AvatarDAOImpl implements AvatarDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class AvatarRowMapper implements RowMapper<Avatar> {
        @Override
        public Avatar mapRow(ResultSet rs, int rowNum) throws SQLException {
            Avatar avatar = new Avatar();
            avatar.setId(rs.getInt("id"));
            avatar.setUrl_immagine(rs.getString("url_immagine"));
            return avatar;
        }
    }
}
