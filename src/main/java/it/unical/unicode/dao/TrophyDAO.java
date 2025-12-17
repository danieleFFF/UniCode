package it.unical.unicode.dao;

import it.unical.unicode.model.Trophy;
import java.util.List;

public interface TrophyDAO {
    List<Trophy> findAllTrophy();
    Trophy findById(int id);
    Trophy findByCode(String code);
}
