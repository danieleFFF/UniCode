package it.unical.unicode.dao;

import it.unical.unicode.model.Trophy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TrophyDAOImpl implements TrophyDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL = "SELECT * FROM trophies";
    private static final String FIND_BY_ID = "SELECT * FROM trophies WHERE id = ?";
    private static final String FIND_BY_CODE = "SELECT * FROM trophies WHERE cod_trophy = ?";
    private static final RowMapper<Trophy> TROFEO_ROW_MAPPER = new TrophyRowMapper();


    public TrophyDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class TrophyRowMapper implements RowMapper<Trophy> {
        @Override
        public Trophy mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trophy trophy = new Trophy();
            trophy.setId(rs.getInt("id"));
            trophy.setName(rs.getString("name"));
            trophy.setDescription(rs.getString("description"));
            trophy.setPoints_trophy(rs.getInt("points_trophy"));
            trophy.setCod_trophy(rs.getString("cod_trophy"));
            return trophy;
        }
    }

    @Override
    public List<Trophy> findAllTrophy(){
        return jdbcTemplate.query(FIND_ALL, TROFEO_ROW_MAPPER);
    }

    @Override
    public Trophy findById(int id){
        return jdbcTemplate.queryForObject(FIND_BY_ID, TROFEO_ROW_MAPPER, id);
    }

    @Override
    public Trophy findByCode(String code){
        return jdbcTemplate.queryForObject(FIND_BY_CODE, TROFEO_ROW_MAPPER, code);
    }
}
