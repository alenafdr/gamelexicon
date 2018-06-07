package ru.study.gamelexicon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.study.gamelexicon.model.Language;
import ru.study.gamelexicon.model.Translation;
import ru.study.gamelexicon.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Service
public class CsvParser {

    @Autowired
    WordService wordService;

    @Autowired
    LanguageService languageService;

    @Autowired
    TranslationService translationService;

    private static final Logger logger = LoggerFactory.getLogger(CsvParser.class);

    public void read(MultipartFile file){
        BufferedReader br;
        try {
            String line;
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                String[]columns = line.split(";");

                String word1Name = columns[0];
                String lang1Name = columns[1];
                String word2Name = columns[2];
                String lang2Name = columns[3];
                Word word1 = checkWord(word1Name);
                Word word2 = checkWord(word2Name);
                Language language1 = checkLang(lang1Name);
                Language language2 = checkLang(lang2Name);

                word1.setLang(language1);
                word2.setLang(language2);

                wordService.save(word1);
                wordService.save(word2);

                setTranslation(word1, word2);
                setTranslation(word2, word1);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private Word checkWord(String name){
        Word word = wordService.getByName(name);
        if(word == null){
            word = new Word();
            word.setName(name);
        }
        return word;
    }

    private Language checkLang(String name){
        Language language = languageService.getByName(name);
        if(language == null){
            language = new Language();
            language.setName(name);
        }
        return language;
    }

    private void setTranslation(Word word1, Word word2){
        Translation translationForWord1 = translationService.getByOrigin(word1);

        if (translationForWord1 == null){
            translationForWord1 = new Translation();
            translationForWord1.setOrigin(word1);
            Set<Word> words = new HashSet<>();
            words.add(word2);
            translationForWord1.setTranslations(words);
            translationService.save(translationForWord1);
        } else if(translationService.getTranslationByLang(word1, word2.getLang()) == null){
            translationForWord1.getTranslations().add(word2);
            translationService.save(translationForWord1);
        }
    }
}
