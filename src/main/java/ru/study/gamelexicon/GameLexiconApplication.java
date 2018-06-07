package ru.study.gamelexicon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class GameLexiconApplication {
	public static void main(String[] args) {
		SpringApplication.run(GameLexiconApplication.class, args);
	}
}
