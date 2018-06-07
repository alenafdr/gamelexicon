package ru.study.gamelexicon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.gamelexicon.dao.GameDao;
import ru.study.gamelexicon.dao.WordDao;
import ru.study.gamelexicon.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final static Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    @Autowired
    GameDao gameDao;

    @Autowired
    SettingService settingService;

    @Autowired
    WordDao wordDao;

    @Autowired
    TranslationService translationService;

    @Autowired
    StepService stepService;

    @Override
    @Transactional
    public List<Game> list() {
        return gameDao.findAll();
    }

    @Override
    @Transactional
    public void save(Game game,  User user) {
        Setting setting = user == null ? settingService.getDefaultSettings() : user.getSetting();
        game.setUser(user);
        game.setLangWordQuestion(setting.getLangQuestion());
        game.setLangWordAnswer(setting.getLangAnswer());
        List<Word> wordsForQuestions = wordDao.getWordsForSteps(setting.getLangQuestion().getName(),
                                                                setting.getLangAnswer().getName(),
                                                                setting.getAmountRepeat(),
                                                                setting.getAmountSteps());

        Set<Step> stepSet = new HashSet<>();
        for (Word word : wordsForQuestions){
            Step step = new Step();
            step.setGame(game);
            step.setWordQuestion(word);
            step.setWordRightAnswer(translationService.getTranslationByLang(word, setting.getLangAnswer()));
            stepSet.add(step);
        }

        game.setSteps(stepSet);
        gameDao.save(game);
    }

    @Override
    @Transactional
    public void update(Game game) {
        gameDao.save(game);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        gameDao.deleteById(id);
    }

    @Override
    @Transactional
    public Game getById(Long id) {
        return gameDao.getOne(id);
    }


    //for tests
    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public void setSettingService(SettingService settingService) {
        this.settingService = settingService;
    }

    public void setWordDao(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    }

    public void setStepService(StepService stepService) {
        this.stepService = stepService;
    }
}
