package ru.study.gamelexicon.service;

import ru.study.gamelexicon.model.Game;
import ru.study.gamelexicon.model.User;

import java.util.List;

public interface GameService {
    List<Game> list();
    void save(Game game, User user);
    void update(Game game);
    void remove(Long id);
    Game getById(Long id);
}
