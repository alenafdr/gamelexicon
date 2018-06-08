package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.gamelexicon.model.Game;

import java.util.List;

public interface GameDao extends JpaRepository<Game, Long> {
    public List<Game> getByUserId(Long id);
}
