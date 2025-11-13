package it.unical.unicode.dao;

import it.unical.unicode.model.Esercizio;
import it.unical.unicode.model.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EsercizioDAOImpl implements EsercizioDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Esercizio> findByLanguage(Integer idLanguage) {
        String sql;
        Object[] params;

        if (idLanguage == null || idLanguage <= 0) {
            sql = "SELECT * FROM exercises";
            params = new Object[]{};
        } else {
            sql = "SELECT * FROM exercises WHERE id_language = ?";
            params = new Object[]{idLanguage};
        }

        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Esercizio.class));
    }

    @Override
    public List<Esercizio> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size) {
        String sortColumn;
        switch (sortBy) {
            case "difficulty":
                sortColumn = "difficulty";
                break;
            case "points":
                sortColumn = "points";
                break;
            case "title":
            default:
                sortColumn = "title";
        }

        if (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc")) {
            order = "asc";
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM exercises");
        Object[] params;

        if (idLanguage != null && idLanguage > 0) {
            sql.append(" WHERE id_language = ?");
            sql.append(" ORDER BY " + sortColumn + " " + order);
            sql.append(" LIMIT ? OFFSET ?");
            params = new Object[]{idLanguage, size, page * size};
        } else {
            sql.append(" ORDER BY " + sortColumn + " " + order);
            sql.append(" LIMIT ? OFFSET ?");
            params = new Object[]{size, page * size};
        }

        return jdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(Esercizio.class));
    }

    @Override
    public Esercizio findById(Integer id) {
        String sql = "SELECT * FROM exercises WHERE id = ?";
        List<Esercizio> result = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Esercizio.class),
                id
        );
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<TestCase> findTestsByExerciseId(Integer id) {
        String sql = "SELECT * FROM exercise_tests WHERE exercise_id = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TestCase.class), id);
    }

    @Override
    public List<Esercizio> findAll(String sortBy, String order) {
        String sql = "SELECT * FROM exercises ORDER BY " + sortBy + " " + order;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Esercizio.class));
    }
}
