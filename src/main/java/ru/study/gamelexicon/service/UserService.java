package ru.study.gamelexicon.service;

import ru.study.gamelexicon.model.User;

import java.util.List;

public interface UserService {
    List<User> list();
    void save(User user);
    void update(User user);
    void remove(Long id);
    User getById(Long id);
    User findByEmail(String email);
    User findByToken(String token);

}
