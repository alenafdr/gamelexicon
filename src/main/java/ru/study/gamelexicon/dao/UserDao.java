package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.gamelexicon.model.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByToken(String token);

}
