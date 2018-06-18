package ru.study.gamelexicon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import ru.study.gamelexicon.rest.json.JacksonStepDeserializer;
import ru.study.gamelexicon.rest.json.JacksonStepSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "steps")
@JsonSerialize(using = JacksonStepSerializer.class)
@JsonDeserialize(using = JacksonStepDeserializer.class)
public class Step extends BaseEntity{

    @ManyToOne(optional = false)
    @JoinColumn(name="game_id", foreignKey = @ForeignKey(name = "game_id_step"))
    private Game game; //привязка к игре

    @ManyToOne(optional = false)
    @JoinColumn(name="word_question_id", foreignKey = @ForeignKey(name = "word_question_id_step"))
    private Word wordQuestion; //вопрос

    @ManyToOne(optional = false)
    @JoinColumn(name="word_right_answer_id", foreignKey = @ForeignKey(name = "word_right_answer_step"))
    private Word wordRightAnswer; // правильный ответ

    @Column(name = "result_answer", nullable = true)
    private boolean resultAnswer; //результат ответа

    @Column(name = "time_give", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd HH:mm:ss")
    private Date timeWhenGive;

    @Column(name = "time_received", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd HH:mm:ss")
    private Date timeWhenReceivedAnswer;


    public Date getTimeWhenGive() {
        return timeWhenGive;
    }


    public void setTimeWhenGive(Date timeWhenGive) {
        this.timeWhenGive = timeWhenGive;
    }

    public Date getTimeWhenReceivedAnswer() {
        return timeWhenReceivedAnswer;
    }

    public void setTimeWhenReceivedAnswer(Date timeWhenReceivedAnswer) {
        this.timeWhenReceivedAnswer = timeWhenReceivedAnswer;
    }

    @Transient
    private List<Word> words; //варианты ответов

    @Transient
    private Word answer;

    public Step() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Word getWordQuestion() {
        return wordQuestion;
    }

    public void setWordQuestion(Word wordQuestion) {
        this.wordQuestion = wordQuestion;
    }

    public Word getWordRightAnswer() {
        return wordRightAnswer;
    }

    public void setWordRightAnswer(Word wordRightAnswer) {
        this.wordRightAnswer = wordRightAnswer;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public boolean isResultAnswer() {
        return resultAnswer;
    }

    public void setResultAnswer(boolean resultAnswer) {
        this.resultAnswer = resultAnswer;
    }

    public Word getAnswer() {
        return answer;
    }

    public void setAnswer(Word answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", wordQuestion=" + wordQuestion +
                ", wordRightAnswer=" + wordRightAnswer +
                ", resultAnswer=" + resultAnswer +
                ", timeWhenGive=" + timeWhenGive +
                ", timeWhenReceivedAnswer=" + timeWhenReceivedAnswer +
                ", words=" + words +
                ", answer=" + answer +
                '}';
    }
}
