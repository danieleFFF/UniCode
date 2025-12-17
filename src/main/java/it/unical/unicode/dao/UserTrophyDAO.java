package it.unical.unicode.dao;

import it.unical.unicode.model.UserTrophy;

import java.util.List;

public interface UserTrophyDAO {

    void assignTrophy(int id_user, int id_trophy);
    boolean hasUserTrophy(int id_user, int id_trophy);
    List<UserTrophy> getUserTrophy();
}
