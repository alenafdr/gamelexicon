package ru.study.gamelexicon.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.study.gamelexicon.rest.json.JacksonGameDeserializer;
import ru.study.gamelexicon.rest.json.JacksonGameSerializer;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "games")
@JsonSerialize(using = JacksonGameSerializer.class)
@JsonDeserialize(using = JacksonGameDeserializer.class)
public class Game extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="user_id", nullable = true)
    private User user; //для привязки игры к юзеру

    @ManyToOne
    @JoinColumn(name="lang_question_id", foreignKey = @ForeignKey(name = "lang_question_id_game"))
    private Language langWordQuestion; //язык вопроса

    @ManyToOne
    @JoinColumn(name="lang_answer_id", foreignKey = @ForeignKey(name = "lang_answer_id_game"))
    private Language langWordAnswer; //язык ответа

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Step> steps;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Language getLangWordQuestion() {
        return langWordQuestion;
    }

    public void setLangWordQuestion(Language langWordQuestion) {
        this.langWordQuestion = langWordQuestion;
    }

    public Language getLangWordAnswer() {
        return langWordAnswer;
    }

    public void setLangWordAnswer(Language langWordAnswer) {
        this.langWordAnswer = langWordAnswer;
    }

    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", user=" + user +
                ", langWordQuestion=" + langWordQuestion +
                ", langWordAnswer=" + langWordAnswer +
                ", steps=" + steps +
                '}';
    }
}
