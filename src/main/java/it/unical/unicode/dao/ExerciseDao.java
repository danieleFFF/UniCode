package it.unical.unicode.dao;

import it.unical.unicode.model.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ExerciseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Exercise> findByLanguageSorted(String language, String sortBy, int page) {
        String sql = """
        SELECT e.id,
               e.titolo AS title,
               e.descrizione AS description,
               e.difficolta AS difficulty,
               l.nome AS language,
               e.punti AS points
        FROM esercizi e
        JOIN linguaggi l ON e.id_linguaggio = l.id
        WHERE l.nome = ?
        ORDER BY e.titolo
        LIMIT 5 OFFSET ?
        """;

        int offset = page * 5;
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Exercise.class), language, offset);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
