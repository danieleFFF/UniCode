package it.unical.unicode.dao;

import it.unical.unicode.model.Exercise;
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
public class ExerciseDAOImpl implements ExerciseDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM exercises";
    private static final String FIND_BY_LANGUAGE = "SELECT * FROM exercises WHERE id_language = ?";
    private static final String FIND_BY_ID = "SELECT * FROM exercises WHERE id = ?";
    private static final String FIND_TESTS_BY_EXERCISE = "SELECT * FROM test_cases WHERE id_exercise = ?";
    private static final String INSERT_EXERCISE="INSERT INTO exercises (id_language, title, description, difficulty, points, solution_demo) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_EXERCISE="DELETE FROM exercises WHERE id = ?";
    private static final List<String> VALID_SORT_COLUMNS = Arrays.asList("title", "difficulty", "points");
    private static final List<String> VALID_ORDERS = Arrays.asList("asc", "desc");

    public ExerciseDAOImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    private String validateSortColumn(String sortBy) {
        if (sortBy == null || !VALID_SORT_COLUMNS.contains(sortBy.toLowerCase()))
            return "title";
        return sortBy.toLowerCase();
    }

    private String validateOrder(String order){
        if (order == null || !VALID_ORDERS.contains(order.toLowerCase()))
            return "asc";
        return order.toLowerCase();
    }

    @Override
    public List<Exercise> findByLanguage(Integer idLanguage) {
        if (idLanguage == null || idLanguage <= 0)
            return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Exercise.class));
        return jdbcTemplate.query(FIND_BY_LANGUAGE, new BeanPropertyRowMapper<>(Exercise.class), idLanguage);
    }

    //Searches exercises by loading a page at a time
    @Override
    public List<Exercise> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size) {
        //Validates sorting parameters and prevents injections
        String sortColumn = validateSortColumn(sortBy);
        String validOrder = validateOrder(order);

        StringBuilder sql = new StringBuilder(FIND_ALL); //Selects all from 'exercises'.
        Object[] params;

        if (idLanguage != null && idLanguage > 0){
            sql.append(" WHERE id_language = ?");
            sql.append(" ORDER BY ").append(sortColumn).append(" ").append(validOrder);
            sql.append(" LIMIT ? OFFSET ?");
            params = new Object[] { idLanguage, size, page * size };
        } else {
            sql.append(" ORDER BY ").append(sortColumn).append(" ").append(validOrder);
            sql.append(" LIMIT ? OFFSET ?");
            params = new Object[] { size, page * size };
        }
        return jdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(Exercise.class));
    }

    @Override
    public Exercise findById(Integer id) {
        List<Exercise> result = jdbcTemplate.query(FIND_BY_ID, new BeanPropertyRowMapper<>(Exercise.class), id);

        return result.isEmpty() ? null : result.getFirst();
    }

    @Override
    public List<TestCase> findTestsByExerciseId(Integer id) {
        return jdbcTemplate.query(FIND_TESTS_BY_EXERCISE, new BeanPropertyRowMapper<>(TestCase.class), id);
    }

    @Override
    public List<Exercise> findAll(String sortBy, String order) {
        String sortColumn = validateSortColumn(sortBy);
        String validOrder = validateOrder(order);
        String sql = FIND_ALL + " ORDER BY " + sortColumn + " " + validOrder;

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Exercise.class));
    }

    @Override
    public int save(Exercise exercise) {
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(connection->{
            PreparedStatement ps=connection.prepareStatement(INSERT_EXERCISE,new String[]{"id"});
            ps.setInt(1,exercise.getId_language());
            ps.setString(2,exercise.getTitle());
            ps.setString(3,exercise.getDescription());
            ps.setString(4,exercise.getDifficulty());
            ps.setInt(5,exercise.getPoints());
            String solutionDemo=exercise.getSolution_demo();
            ps.setString(6,Objects.requireNonNullElse(solutionDemo,""));
            return ps;
        },keyHolder);
        Number key=keyHolder.getKey();
        return Objects.requireNonNullElseGet(key,()->Objects.requireNonNull(keyHolder.getKey())).intValue();
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_EXERCISE,id);
    }
}
