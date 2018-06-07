package ru.study.gamelexicon.service;

import ru.study.gamelexicon.model.Word;

import java.util.List;

public interface WordService {
    List<Word> list();
    void save(Word word);
    void update(Word word);
    void remove(Long id);
    Word getById(Long id);
    Word getByName(String name);
    List<Word> getWordsGuessedAboveThresholdByUserId(Long id, int limit);
    List<Word> getMostHitsByUserId(Long id, int limit);
    List<Word> getMostMissesByUserId(Long id, int limit);

}
