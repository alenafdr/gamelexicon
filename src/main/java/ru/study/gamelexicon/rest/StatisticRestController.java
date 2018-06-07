package ru.study.gamelexicon.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.gamelexicon.model.Word;
import ru.study.gamelexicon.service.WordService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/statistic/")
public class StatisticRestController {

    @Autowired
    WordService wordService;

    @GetMapping(value = "{id}/abovethreshold")
    public ResponseEntity<List<Word>> getWordsAboveThreshold(@PathVariable("id") Long id, @RequestParam("limit") int limit){
        if(limit == 0){
            limit = 10;
        }
        return new ResponseEntity<List<Word>>(wordService.getWordsGuessedAboveThresholdByUserId(id, limit), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/mosthits")
    public ResponseEntity<List<Word>> getMostHits(@PathVariable("id") Long id, @RequestParam("limit") int limit){
        if(limit == 0){
            limit = 10;
        }
        return new ResponseEntity<List<Word>>(wordService.getMostHitsByUserId(id, limit), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/mostmisses")
    public ResponseEntity<List<Word>> getMostMisses(@PathVariable("id") Long id, @RequestParam("limit") int limit){
        if(limit == 0){
            limit = 10;
        }
        return new ResponseEntity<List<Word>>(wordService.getMostMissesByUserId(id, limit), HttpStatus.OK);
    }
}
