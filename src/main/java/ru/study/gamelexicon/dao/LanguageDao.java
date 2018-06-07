package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.gamelexicon.model.Language;

public interface LanguageDao extends JpaRepository<Language, Long> {
    public Language getByName(String name);
}
