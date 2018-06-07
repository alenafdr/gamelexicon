package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.gamelexicon.model.Step;

public interface StepDao extends JpaRepository<Step, Long> {
}
