package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.study.gamelexicon.model.Translation;
import ru.study.gamelexicon.model.Word;

public interface TranslationDao extends JpaRepository<Translation, Long> {
    public Translation getByOrigin(Word word);

}
