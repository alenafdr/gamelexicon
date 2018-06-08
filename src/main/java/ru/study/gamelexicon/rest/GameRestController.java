package ru.study.gamelexicon.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.gamelexicon.model.Game;
import ru.study.gamelexicon.model.Role;
import ru.study.gamelexicon.model.User;
import ru.study.gamelexicon.service.GameService;
import ru.study.gamelexicon.service.UserService;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/games/")
public class GameRestController {

    private static final Logger logger = LoggerFactory.getLogger(GameRestController.class);

    @Autowired
    GameService gameService;

    @Autowired
    UserService userService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Game>> list(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("X-Auth-Token");

        User user = userService.findByToken(header);
        List<Game> games;

        if (user.getRoles().contains(new Role("ROLE_ADMIN"))){
            games = this.gameService.list();
        } else {
            games = this.gameService.listByUserId(user.getId());
        }

        if (games.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Game> create(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("X-Auth-Token");
        User user = null;
        if (header != null){
            user = userService.findByToken(header);
        }
        Game game = new Game();
        this.gameService.save(game, user);

        logger.info("Created game", game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Game> read(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Game game = this.gameService.getById(id);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Game> update(@RequestBody @Valid Game game) {
        if (game == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.gameService.update(game);
        logger.info("Updated game", game);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Game> delete(@PathVariable("id") Long id) {
        Game game = this.gameService.getById(id);

        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.gameService.remove(id);
        logger.info("Removed game", game);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
