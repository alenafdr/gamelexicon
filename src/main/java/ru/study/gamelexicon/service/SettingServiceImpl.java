package ru.study.gamelexicon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.gamelexicon.dao.LanguageDao;
import ru.study.gamelexicon.dao.SettingDao;
import ru.study.gamelexicon.model.Setting;

import java.util.List;

@Service
public class SettingServiceImpl implements SettingService {

    private static final Logger logger = LoggerFactory.getLogger(SettingServiceImpl.class);

    @Autowired
    SettingDao settingDao;

    @Autowired
    LanguageDao languageDao;

    @Override
    public List<Setting> list() {
        return settingDao.findAll();
    }

    @Override
    @Transactional
    public void save(Setting setting) {
        settingDao.save(setting);
    }

    @Override
    @Transactional
    public void update(Setting setting) {
        settingDao.save(setting);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        settingDao.deleteById(id);
    }

    @Override
    @Transactional
    public Setting getById(Long id) {
        return settingDao.getOne(id);
    }

    @Override
    @Transactional
    public Setting getDefaultSettings() {
        Setting setting = new Setting();
        setting.setAmountRepeat(5);
        setting.setAmountSteps(20);
        setting.setSecForAnswer(5);
        setting.setLangQuestion(languageDao.getByName("русский"));
        setting.setLangAnswer(languageDao.getByName("english"));
        return setting;
    }

    //for tests
    public void setLanguageDao(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }

    public Integer getSecForAnswerByStep(Long stepId){
        return settingDao.getSecForAnswerByStep(stepId);
    }
}
