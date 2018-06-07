package ru.study.gamelexicon.service;

import ru.study.gamelexicon.model.Language;

import java.util.List;

public interface LanguageService {
    List<Language> list();
    void save(Language language);
    void update(Language language);
    void remove(Long id);
    Language getById(Long id);
    Language getByName(String name);
}
