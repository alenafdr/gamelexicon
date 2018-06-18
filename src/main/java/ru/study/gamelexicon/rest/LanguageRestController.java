package ru.study.gamelexicon.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.gamelexicon.model.Language;
import ru.study.gamelexicon.service.LanguageService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/languages/")
public class LanguageRestController {

    private static final Logger logger = LoggerFactory.getLogger(LanguageRestController.class);

    @Autowired
    LanguageService languageService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Language>> list(){
        List<Language> languages = this.languageService.list();

        if (languages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Language> create(@RequestBody @Valid Language language){
       if (language == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.languageService.save(language);
        logger.info("Created language", language);
        return new ResponseEntity<>(language, HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Language> read(@PathVariable("id") Long langId){
        if (langId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Language language = this.languageService.getById(langId);

        if (language == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Language> update(@RequestBody @Valid Language language){
        if (language == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.languageService.update(language);

        logger.info("Updated language", language);
        return new ResponseEntity<>(language, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Language> delete(@PathVariable("id") Long langId){
        Language language = this.languageService.getById(langId);

        if (language == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.languageService.remove(langId);

        logger.info("Removed language", language);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
