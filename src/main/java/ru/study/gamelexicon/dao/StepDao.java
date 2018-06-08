package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.study.gamelexicon.model.Step;

import java.util.List;

public interface StepDao extends JpaRepository<Step, Long> {
    @Query(nativeQuery = true,
    value = "SELECT steps.* \n" +
            "FROM steps \n" +
            "INNER JOIN games ON steps.game_id = games.id\n" +
            "WHERE games.user_id = :userId")
    public List<Step> getListByUserId(@Param("userId") Long id);
}
