package ru.study.gamelexicon.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.study.gamelexicon.rest.json.JacksonSettingDeserializer;
import ru.study.gamelexicon.rest.json.JacksonSettingSerializer;

import javax.persistence.*;

@Entity
@Table(name = "settings")
@JsonSerialize(using = JacksonSettingSerializer.class)
@JsonDeserialize(using = JacksonSettingDeserializer.class)
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="lang_question_id", foreignKey = @ForeignKey(name = "lang_question_id_settings"), nullable = true)
    private Language langQuestion;

    @ManyToOne
    @JoinColumn(name="lang_answer_id", foreignKey = @ForeignKey(name = "lang_answer_id_settings"), nullable = true)
    private Language langAnswer;

    @Column(name = "amount_steps")
    private int amountSteps;

    @Column(name = "sec_for_answer")
    private int secForAnswer;

    @Column(name = "amount_repeat")
    private int amountRepeat;

    public Setting() {
    }

    public Setting(Language langQuestion, Language langAnswer, int amountSteps, int secForAnswer, int amountRepeat) {
        this.langQuestion = langQuestion;
        this.langAnswer = langAnswer;
        this.amountSteps = amountSteps;
        this.secForAnswer = secForAnswer;
        this.amountRepeat = amountRepeat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLangQuestion() {
        return langQuestion;
    }

    public void setLangQuestion(Language langQuestion) {
        this.langQuestion = langQuestion;
    }

    public Language getLangAnswer() {
        return langAnswer;
    }

    public void setLangAnswer(Language langAnswer) {
        this.langAnswer = langAnswer;
    }

    public int getAmountSteps() {
        return amountSteps;
    }

    public void setAmountSteps(int amountSteps) {
        this.amountSteps = amountSteps;
    }

    public int getSecForAnswer() {
        return secForAnswer;
    }

    public void setSecForAnswer(int secForAnswer) {
        this.secForAnswer = secForAnswer;
    }

    public int getAmountRepeat() {
        return amountRepeat;
    }

    public void setAmountRepeat(int amountRepeat) {
        this.amountRepeat = amountRepeat;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", langQuestion=" + langQuestion +
                ", langAnswer=" + langAnswer +
                ", amountSteps=" + amountSteps +
                ", secForAnswer=" + secForAnswer +
                ", amountRepeat=" + amountRepeat +
                '}';
    }
}
