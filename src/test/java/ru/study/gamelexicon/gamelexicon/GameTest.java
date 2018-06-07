package ru.study.gamelexicon.gamelexicon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.gamelexicon.dao.GameDao;
import ru.study.gamelexicon.dao.LanguageDao;
import ru.study.gamelexicon.dao.WordDao;
import ru.study.gamelexicon.model.*;
import ru.study.gamelexicon.service.GameServiceImpl;
import ru.study.gamelexicon.service.SettingServiceImpl;
import ru.study.gamelexicon.service.TranslationService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TranslationService.class , WordDao.class, GameDao.class, LanguageDao.class})
public class GameTest {
    private GameServiceImpl gameService;
    private SettingServiceImpl settingService;
    private TranslationService mockTranslationService;
    private WordDao mockWordDao;
    private GameDao mockGameDao;
    private LanguageDao mockLanguageDao;

    private final static Logger logger = LoggerFactory.getLogger(GameTest.class);

    @Before
    public void init(){
        gameService = new GameServiceImpl();
        settingService = new SettingServiceImpl();
        mockTranslationService = PowerMockito.mock(TranslationService.class);
        mockWordDao = PowerMockito.mock(WordDao.class);
        mockGameDao = PowerMockito.mock(GameDao.class);
        mockLanguageDao = PowerMockito.mock(LanguageDao.class);
        gameService.setSettingService(settingService);
        gameService.setTranslationService(mockTranslationService);
        gameService.setWordDao(mockWordDao);
        gameService.setGameDao(mockGameDao);
        settingService.setLanguageDao(mockLanguageDao);
    }


    @Test
    public void gameCreateForNotNullUser(){
        User user = getUser();
        Game game = new Game();

        setReturnForWordDao(user);
        setReturnForTranslationService(user);

        gameService.save(game, user);

        assertNotNull(game.getSteps());
        assertFalse(game.getSteps().isEmpty());
        assertNotNull(game.getUser());
        assertNotNull(game.getLangWordQuestion());
        assertNotNull(game.getLangWordAnswer());
    }

    @Test
    public void gameCreateForNullUser(){
        Game game = new Game();
        setLangForDefaultSetting();

        gameService.save(game, null);

        assertNotNull(game.getSteps());
        assertNull(game.getUser());
        assertNotNull(game.getLangWordQuestion());
        assertNotNull(game.getLangWordAnswer());
    }

    private User getUser(){
        User user = new User();
        Language langQuestion = new Language("русский");
        Language langAnswer = new Language("english");
        Setting setting = new Setting(langQuestion, langAnswer, 20, 5, 5);
        user.setSetting(setting);
        return user;
    }

    private List<Word> getTestWordsForSteps(){
        List<Word> words = new ArrayList<>();
        Language langQuestion = new Language("русский");
        words.add(new Word("стол", langQuestion));
        words.add(new Word("кот", langQuestion));
        words.add(new Word("дом", langQuestion));

        return words;
    }

    private void setReturnForWordDao(User user){
        when(mockWordDao.getWordsForSteps(  user.getSetting().getLangQuestion().getName(),
                user.getSetting().getLangAnswer().getName(),
                user.getSetting().getAmountRepeat(),
                user.getSetting().getAmountSteps()))
                .thenReturn(getTestWordsForSteps());
    }

    private void setReturnForTranslationService(User user){
        Language langQuestion = user.getSetting().getLangQuestion();
        Language langAnswer = user.getSetting().getLangAnswer();

        when(mockTranslationService.getTranslationByLang(new Word("стол", langQuestion), langAnswer))
                .thenReturn(new Word("table", langAnswer));
        when(mockTranslationService.getTranslationByLang(new Word("кот", langQuestion), langAnswer))
                .thenReturn(new Word("cat", langAnswer));
        when(mockTranslationService.getTranslationByLang(new Word("дом", langQuestion), langAnswer))
                .thenReturn(new Word("home", langAnswer));
    }

    private void setLangForDefaultSetting(){
        when(mockLanguageDao.getByName("русский")).thenReturn(new Language("русский"));
        when(mockLanguageDao.getByName("english")).thenReturn(new Language("english"));
    }
}
