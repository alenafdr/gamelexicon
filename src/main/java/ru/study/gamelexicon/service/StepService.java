package ru.study.gamelexicon.service;

import ru.study.gamelexicon.model.Step;

import java.util.List;

public interface StepService {
    List<Step> list();
    void save(Step step);
    void update(Step step);
    void remove(Long id);
    Step getById(Long id);
    List<Step> listByUserId(Long id);
}
