package ru.study.gamelexicon.rest;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.study.gamelexicon.model.Game;
import ru.study.gamelexicon.model.User;
import ru.study.gamelexicon.service.GameService;
import ru.study.gamelexicon.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class LoginRestController {

    @Autowired
    UserService userService;

    @Autowired
    GameService gameService;

    private static final Logger logger = LoggerFactory.getLogger(LoginRestController.class);

    @GetMapping("/")
    public ResponseEntity<Game> empty() {

        /*User user = userService.getById(3L);
        Game game = new Game();
        gameService.save(game, user);*/
        return new ResponseEntity<Game>(gameService.getById(20L), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestParam("login") String email,
                                               @RequestParam("password") String password,
                                               @RequestParam("first_name") String firstName,
                                               @RequestParam("last_name") String lastName){

        if (userService.findByEmail(email) != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(email);
        user. setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        String jwtToken = getToken(user.getEmail());
        user.setToken(jwtToken);
        userService.save(user);
        logger.info("User registered", user);
        return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("login") String email,
                                        @RequestParam("password") String password) throws ServletException {

        logger.debug(email + " " + password);

        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User userNew = userService.findByEmail(email);
        if (userNew == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String pwd = userNew.getPassword();

        if (!password.equals(pwd)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String jwtToken = getToken(email);

        userNew.setToken(jwtToken);
        userService.update(userNew);
        logger.info("User login", userNew);
        return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(ServletRequest servletRequest){
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String header = httpServletRequest.getHeader("X-Auth-Token");

        User user = userService.findByToken(header);
        user.setToken(getToken(user.getEmail()));

        userService.update(user);

        logger.info("User logout", user);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    private String getToken(String email){
        return Jwts.builder().setSubject(email).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
    }
}
