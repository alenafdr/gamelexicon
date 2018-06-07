package ru.study.gamelexicon.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.gamelexicon.model.Step;
import ru.study.gamelexicon.model.Word;
import ru.study.gamelexicon.service.StepService;
import ru.study.gamelexicon.service.WordService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/steps/")
public class StepRestController {

    public static final Logger logger = LoggerFactory.getLogger(StepRestController.class);

    @Autowired
    StepService stepService;

    @Autowired
    WordService wordService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Step>> list(){
        List<Step> steps = this.stepService.list();

        if (steps.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(steps, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Step> create(@RequestBody @Valid Step step){
        if (step == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.stepService.save(step);
        logger.info("Created step", step);
        return new ResponseEntity<>(step, HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Step> read(@PathVariable("id") Long id){
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Step step = this.stepService.getById(id);

        if (step == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(step, HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Step> update(@RequestBody @Valid Step step){
        if (step == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Word answer = step.getAnswer();
        step = stepService.getById(step.getId());
        step.setAnswer(answer);
        stepService.update(step);
        logger.info("Updated step", step);
        return new ResponseEntity<>(step, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Step> delete(@PathVariable("id") Long id){
        Step step = this.stepService.getById(id);

        if (step == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.stepService.remove(id);
        logger.info("Removed step", step);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
