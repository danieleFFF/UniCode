package it.unical.unicode.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import it.unical.unicode.dto.CoursesTopicsDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CoursesTopicsDAOImpl implements CoursesTopicsDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_TOPICS_BY_LANGUAGE_ID="SELECT * FROM courses_topics WHERE id_language = ? ORDER BY id ASC";
    private static final RowMapper<CoursesTopicsDTO> TOPIC_MAPPER=new TopicRowMapper();
    public CoursesTopicsDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private static class TopicRowMapper implements RowMapper<CoursesTopicsDTO>{
        @Override
        public CoursesTopicsDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
            CoursesTopicsDTO topic=new CoursesTopicsDTO();
            topic.setId(rs.getInt("id"));
            topic.setIdLanguage(rs.getInt("id_language"));
            topic.setTitle(rs.getString("title"));
            topic.setIntroduction(rs.getString("introduction"));
            topic.setCodeExample(rs.getString("code_example"));
            topic.setKeyConcepts(rs.getString("key_concepts"));
            topic.setUrlVideo(rs.getString("url_video"));
            topic.setDifficulty(rs.getString("difficulty"));
            return topic;
        }
    }

    @Override
    public List<CoursesTopicsDTO> getTopicsByLanguageId(int languageId){
        return jdbcTemplate.query(FIND_TOPICS_BY_LANGUAGE_ID,TOPIC_MAPPER,languageId);
    }
}
