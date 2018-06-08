package ru.study.gamelexicon.service;

import ru.study.gamelexicon.model.Setting;

import java.util.List;

public interface SettingService {
    List<Setting> list();
    void save(Setting setting);
    void update(Setting setting);
    void remove(Long id);
    Setting getById(Long id);
    Setting getDefaultSettings();
    Integer getSecForAnswerByStep(Long stepId);
    Setting getByUserId(Long id);
}
