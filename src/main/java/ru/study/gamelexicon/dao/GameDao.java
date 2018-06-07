package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.gamelexicon.model.Game;

public interface GameDao extends JpaRepository<Game, Long> {
}
