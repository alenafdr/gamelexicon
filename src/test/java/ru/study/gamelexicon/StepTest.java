package ru.study.gamelexicon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.study.gamelexicon.dao.SettingDao;
import ru.study.gamelexicon.dao.StepDao;
import ru.study.gamelexicon.dao.WordDao;
import ru.study.gamelexicon.model.Language;
import ru.study.gamelexicon.model.Step;
import ru.study.gamelexicon.model.Word;
import ru.study.gamelexicon.service.StepServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ StepDao.class, WordDao.class, SettingDao.class})
public class StepTest {

	public static final Logger logger = LoggerFactory.getLogger(StepTest.class);

	private StepServiceImpl stepService;
	private StepDao mockStepDao;
	private WordDao mockWordDao;
	private SettingDao mockSettingDao;

	@Before
	public void init(){
		stepService = new StepServiceImpl();
		mockStepDao = PowerMockito.mock(StepDao.class);
		mockWordDao = PowerMockito.mock(WordDao.class);
		mockSettingDao = PowerMockito.mock(SettingDao.class);
		stepService.setStepDao(mockStepDao);
		stepService.setWordDao(mockWordDao);
		stepService.setSettingDao(mockSettingDao);
	}

	@Test
	public void compareWords() {
		Word word1 = new Word();
		word1.setId(1L);
		word1.setName("кот");
		Word word2 = new Word();
		word2.setId(1L);
		word2.setName("кот");

		assertTrue(word1.equals(word2));

		word2.setName("собака");

		assertFalse(word1.equals(word2));

		word2.setName("кот");
		word2.setId(2L);

		assertFalse(word1.equals(word2));
	}

	@Test
	public void stepGiveWithTranslation(){
		setStepForGetOne();
		setWordsForGetByLangRandom();
		Step step = stepService.getById(1L);

		assertNotNull(step.getWords());
		assertFalse(step.getWords().isEmpty());
		assertNotNull(step.getTimeWhenGive());
	}

	@Test
	public void stepReceivedWrongAnswer(){
		setStepForGetOne();
		setWordsForGetByLangRandom();
		setSecForAnswer();
		Step step = stepService.getById(1L);
		step.setAnswer(new Word("dog", new Language("en")));
		stepService.update(step);

		assertFalse(step.isResultAnswer());
	}

	@Test
	public void stepReceivedRightAnswerRightTime(){
		setStepForGetOne();
		setWordsForGetByLangRandom();
		setSecForAnswer();
		Step step = stepService.getById(1L);

		step.setAnswer(new Word("cat", new Language("en")));
		stepService.update(step);
		assertTrue(step.isResultAnswer());

	}

	@Test
	public void stepReceivedRightAnswerWrongTime(){
		setStepForGetOne();
		setWordsForGetByLangRandom();
		setSecForAnswer();
		Step step = stepService.getById(1L);
		step.setTimeWhenGive(new Date(System.currentTimeMillis() - 5000));
		step.setAnswer(new Word("cat", new Language("en")));

		assertFalse(step.isResultAnswer());
	}

	private void setStepForGetOne(){
		Step step = new Step();
		step.setId(1L);
		step.setWordQuestion(new Word("кот", new Language("ru")));
		step.setWordRightAnswer(new Word("cat", new Language("en")));
		when(mockStepDao.getOne(1L)).thenReturn(step);
	}

	private void setWordsForGetByLangRandom() {
		List<Word> words = new ArrayList<>();
		words.add(new Word("dog", new Language("en")));
		words.add(new Word("home", new Language("en")));
		words.add(new Word("table", new Language("en")));
		when(mockWordDao.getByLangRandom("en", 3)).thenReturn(words);
	}

	private void setSecForAnswer(){
		when(mockSettingDao.getSecForAnswerByStep(1L)).thenReturn(5);
	}
}
