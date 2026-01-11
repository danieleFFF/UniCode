package it.unical.unicode.dao;

import it.unical.unicode.model.Esercizio;
import it.unical.unicode.model.TestCase;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class EsercizioDAOImpl implements EsercizioDAO {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL = "SELECT * FROM exercises";
    private static final String FIND_BY_LANGUAGE = "SELECT * FROM exercises WHERE id_language = ?";
    private static final String FIND_BY_ID = "SELECT * FROM exercises WHERE id = ?";
    private static final String FIND_TESTS_BY_EXERCISE = "SELECT * FROM test_cases WHERE id_exercise = ?";
    private static final String INSERT_EXERCISE="INSERT INTO exercises (id_language, title, description, difficulty, points, solution_demo) VALUES (?, ?, ?, ?, ?, ?)";
    private static final List<String> VALID_SORT_COLUMNS = Arrays.asList("title", "difficulty", "points");
    private static final List<String> VALID_ORDERS = Arrays.asList("asc", "desc");

    public EsercizioDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String validateSortColumn(String sortBy) {
        if (sortBy == null || !VALID_SORT_COLUMNS.contains(sortBy.toLowerCase())) {
            return "title";
        }
        return sortBy.toLowerCase();
    }

    private String validateOrder(String order) {
        if (order == null || !VALID_ORDERS.contains(order.toLowerCase())) {
            return "asc";
        }
        return order.toLowerCase();
    }

    @Override
    public List<Esercizio> findByLanguage(Integer idLanguage) {
        if (idLanguage == null || idLanguage <= 0) {
            return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Esercizio.class));
        }
        return jdbcTemplate.query(FIND_BY_LANGUAGE, new BeanPropertyRowMapper<>(Esercizio.class), idLanguage);
    }

    @Override
    public List<Esercizio> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size) {
        String sortColumn = validateSortColumn(sortBy);
        String validOrder = validateOrder(order);
        StringBuilder sql = new StringBuilder(FIND_ALL);
        Object[] params;

        if (idLanguage != null && idLanguage > 0) {
            sql.append(" WHERE id_language = ?");
            sql.append(" ORDER BY ").append(sortColumn).append(" ").append(validOrder);
            sql.append(" LIMIT ? OFFSET ?");
            params = new Object[] { idLanguage, size, page * size };
        } else {
            sql.append(" ORDER BY ").append(sortColumn).append(" ").append(validOrder);
            sql.append(" LIMIT ? OFFSET ?");
            params = new Object[] { size, page * size };
        }
        return jdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(Esercizio.class));
    }

    @Override
    public Esercizio findById(Integer id) {
        List<Esercizio> result = jdbcTemplate.query(FIND_BY_ID, new BeanPropertyRowMapper<>(Esercizio.class), id);
        return result.isEmpty() ? null : result.getFirst();
    }

    @Override
    public List<TestCase> findTestsByExerciseId(Integer id) {
        return jdbcTemplate.query(FIND_TESTS_BY_EXERCISE, new BeanPropertyRowMapper<>(TestCase.class), id);
    }

    @Override
    public List<Esercizio> findAll(String sortBy, String order) {
        String sortColumn = validateSortColumn(sortBy);
        String validOrder = validateOrder(order);
        String sql = FIND_ALL + " ORDER BY " + sortColumn + " " + validOrder;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Esercizio.class));
    }

    @Override
    public int save(Esercizio esercizio) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_EXERCISE, new String[]{"id"});
            ps.setInt(1, esercizio.getId_language());
            ps.setString(2, esercizio.getTitle());
            ps.setString(3, esercizio.getDescription());
            ps.setString(4, esercizio.getDifficulty());
            ps.setInt(5, esercizio.getPoints());
            ps.setString(6, esercizio.getSolution_demo() != null ? esercizio.getSolution_demo() : "");
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}