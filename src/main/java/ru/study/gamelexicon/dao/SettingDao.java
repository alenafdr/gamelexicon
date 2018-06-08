package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.study.gamelexicon.model.Setting;

public interface SettingDao extends JpaRepository<Setting, Long> {
    @Query(nativeQuery = true,
    value = "SELECT settings.sec_for_answer " +
            "FROM steps " +
            "INNER JOIN games ON steps.game_id = games.id " +
            "INNER JOIN users ON games.user_id = users.id " +
            "INNER JOIN settings ON users.setting_id = settings.id " +
            "WHERE steps.id = :id")
    public Integer getSecForAnswerByStep(@Param("id") Long id);

    @Query(nativeQuery = true,
    value = "SELECT settings.* \n" +
            "FROM settings \n" +
            "INNER JOIN users ON users.setting_id = settings.id\n" +
            "WHERE users.id = :id")
    public Setting getByUserId(@Param("id") Long id);
}
