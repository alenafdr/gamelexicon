package ru.study.gamelexicon.service;

import ru.study.gamelexicon.model.Language;
import ru.study.gamelexicon.model.Translation;
import ru.study.gamelexicon.model.Word;

import java.util.List;


public interface TranslationService {
    List<Translation> list();
    void save(Translation translation);
    void update(Translation translation);
    void remove(Long id);
    Translation getById(Long id);
    Translation getByOrigin(Word word);
    Word getTranslationByLang(Word origin, Language languageToTranslate);
}
