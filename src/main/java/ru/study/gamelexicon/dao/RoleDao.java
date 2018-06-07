package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.gamelexicon.model.Role;

public interface RoleDao extends JpaRepository<Role, Long> {
    public Role getByName(String name);
}
