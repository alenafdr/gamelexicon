package ru.study.gamelexicon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.gamelexicon.dao.TranslationDao;
import ru.study.gamelexicon.model.Language;
import ru.study.gamelexicon.model.Translation;
import ru.study.gamelexicon.model.Word;

import java.util.List;

@Service
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    TranslationDao translationDao;

    @Override
    public List<Translation> list() {
        return translationDao.findAll();
    }

    @Override
    @Transactional
    public void save(Translation translation) {
        translationDao.save(translation);
    }

    @Override
    @Transactional
    public void update(Translation translation) {
        translationDao.save(translation);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        translationDao.deleteById(id);
    }

    @Override
    @Transactional
    public Translation getById(Long id) {
        return translationDao.getOne(id);
    }

    @Override
    @Transactional
    public Translation getByOrigin(Word word) {
        return translationDao.getByOrigin(word);
    }

    @Override
    @Transactional
    public Word getTranslationByLang(Word word, Language language){
        Translation translation = translationDao.getByOrigin(word);
        return translation.getTranslationByLang(language);
    }
}
