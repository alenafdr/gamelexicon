package ru.study.gamelexicon.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.gamelexicon.model.Word;
import ru.study.gamelexicon.service.WordService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/words/")
public class WordRestController {

    public static final Logger logger = LoggerFactory.getLogger(WordRestController.class);

    @Autowired
    WordService wordService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Word>> list() {
        List<Word> words = this.wordService.list();

        if (words.isEmpty()) {
            logger.debug(words.toString());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(words, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Word> create(@RequestBody @Valid Word word) {
        if (word == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.wordService.save(word);
        logger.info("Word created", word);
        return new ResponseEntity<>(word, HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Word> read(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Word word = this.wordService.getById(id);

        if (word == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(word, HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Word> update(@RequestBody @Valid Word word) {
        if (word == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.wordService.update(word);
        logger.info("Word updated", word);
        return new ResponseEntity<>(word, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Word> delete(@PathVariable("id") Long id) {
        Word word = this.wordService.getById(id);

        if (word == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.wordService.remove(id);
        logger.info("Word removed", word);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
