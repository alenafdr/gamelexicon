package ru.study.gamelexicon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.gamelexicon.dao.WordDao;
import ru.study.gamelexicon.model.Word;

import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    WordDao wordDao;

    @Override
    public List<Word> list() {
        return wordDao.findAll();
    }

    @Override
    @Transactional
    public void save(Word word) {
        wordDao.save(word);
    }

    @Override
    @Transactional
    public void update(Word word) {
        wordDao.save(word);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        wordDao.deleteById(id);
    }

    @Override
    @Transactional
    public Word getById(Long id) {
        return wordDao.getOne(id);
    }

    @Override
    @Transactional
    public Word getByName(String name) {
        return wordDao.getByName(name);
    }

    @Override
    @Transactional
    public List<Word> getWordsGuessedAboveThresholdByUserId(Long id, int limit) {
        return wordDao.getWordsGuessedAboveThresholdByUserId(id, limit);
    }

    @Override
    @Transactional
    public List<Word> getMostHitsByUserId(Long id, int limit) {
        return wordDao.getMostHitsByUserId(id, limit);
    }

    @Override
    @Transactional
    public List<Word> getMostMissesByUserId(Long id, int limit) {
        return wordDao.getMostMissesByUserId(id, limit);
    }
}
