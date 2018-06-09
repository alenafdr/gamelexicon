package ru.study.gamelexicon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.study.gamelexicon.model.Word;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestTest {

    @Test
    public void test() throws Exception{
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
}
