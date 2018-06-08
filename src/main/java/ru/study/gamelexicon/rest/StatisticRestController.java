package ru.study.gamelexicon.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.gamelexicon.model.Role;
import ru.study.gamelexicon.model.User;
import ru.study.gamelexicon.model.Word;
import ru.study.gamelexicon.service.UserService;
import ru.study.gamelexicon.service.WordService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/statistic/")
public class StatisticRestController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticRestController.class);

    @Autowired
    WordService wordService;

    @Autowired
    UserService userService;

    @GetMapping(value = "{id}/abovethreshold")
    public ResponseEntity<List<Word>> getWordsAboveThreshold(@PathVariable("id") Long id,
                                                             @RequestParam("limit") int limit,
                                                             ServletRequest servletRequest){
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("X-Auth-Token");
        User user = userService.findByToken(header);
        if (id.equals(user.getId()) || user.getRoles().contains(new Role("ROLE_ADMIN"))){
            List<Word> words = wordService.getWordsGuessedAboveThresholdByUserId(id, limit);
            return new ResponseEntity<List<Word>>(words, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "{id}/mosthits")
    public ResponseEntity<List<Word>> getMostHits(@PathVariable("id") Long id,
                                                  @RequestParam("limit") int limit,
                                                  ServletRequest servletRequest){
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("X-Auth-Token");
        User user = userService.findByToken(header);
        if (id.equals(user.getId()) || user.getRoles().contains(new Role("ROLE_ADMIN"))){
            List<Word> words = wordService.getMostHitsByUserId(id, limit);
            return new ResponseEntity<List<Word>>(words, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "{id}/mostmisses")
    public ResponseEntity<List<Word>> getMostMisses(@PathVariable("id") Long id,
                                                    @RequestParam("limit") int limit,
                                                    ServletRequest servletRequest){
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("X-Auth-Token");
        User user = userService.findByToken(header);
        if (id.equals(user.getId()) || user.getRoles().contains(new Role("ROLE_ADMIN"))){
            List<Word> words = wordService.getMostMissesByUserId(id, limit);
            return new ResponseEntity<List<Word>>(words, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
