package ru.study.gamelexicon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.gamelexicon.dao.LanguageDao;
import ru.study.gamelexicon.model.Language;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageDao languageDao;

    @Override
    public List<Language> list() {
        return languageDao.findAll();
    }

    @Override
    @Transactional
    public void save(Language language) {
        languageDao.save(language);
    }

    @Override
    @Transactional
    public void update(Language language) {
        languageDao.save(language);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        languageDao.deleteById(id);
    }

    @Override
    @Transactional
    public Language getById(Long id) {
        return languageDao.getOne(id);
    }

    @Override
    @Transactional
    public Language getByName(String name) {
        return languageDao.getByName(name);
    }
}
