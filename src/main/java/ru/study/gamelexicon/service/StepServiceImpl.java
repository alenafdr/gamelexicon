package ru.study.gamelexicon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.gamelexicon.dao.SettingDao;
import ru.study.gamelexicon.dao.StepDao;
import ru.study.gamelexicon.dao.WordDao;
import ru.study.gamelexicon.model.Step;
import ru.study.gamelexicon.model.Word;

import java.util.Date;
import java.util.List;

@Service
public class StepServiceImpl implements StepService {

    @Autowired
    StepDao stepDao;

    @Autowired
    WordDao wordDao;

    @Autowired
    SettingDao settingDao;

    private static final Logger logger = LoggerFactory.getLogger(StepServiceImpl.class);

    @Override
    @Transactional
    public List<Step> list() {
        return stepDao.findAll();
    }

    @Override
    @Transactional
    public void save(Step step) {
        stepDao.save(step);
    }

    @Override
    @Transactional
    public void update(Step step) {
        if (step.getAnswer() != null){
            Date timeNow = new Date();
            long difference = timeNow.getTime() - step.getTimeWhenGive().getTime();
            Integer secForAnswer = settingDao.getSecForAnswerByStep(step.getId());
            if (secForAnswer == null) secForAnswer = 5;
            if(difference < secForAnswer*1000){
                step.setResultAnswer(step.getAnswer().equals(step.getWordRightAnswer()));
                step.setTimeWhenReceivedAnswer(timeNow);
            }
        }
        stepDao.save(step);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        stepDao.deleteById(id);
    }

    @Override
    @Transactional
    public Step getById(Long id) {
       Step step = stepDao.getOne(id);
        List<Word> words = wordDao.getByLangRandom(step.getWordRightAnswer().getLang().getName(), 3);
        words.add(step.getWordRightAnswer());
        step.setWords(words);

        if(step.getTimeWhenGive() == null){
            step.setTimeWhenGive(new Date());
            update(step);
        }
        return step;
    }

    //for tests
    public void setStepDao(StepDao stepDao) {
        this.stepDao = stepDao;
    }

    public void setWordDao(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    public void setSettingDao(SettingDao settingDao) {
        this.settingDao = settingDao;
    }
}
