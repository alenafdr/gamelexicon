package ru.study.gamelexicon.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.study.gamelexicon.rest.json.JacksonWordDeserializer;
import ru.study.gamelexicon.rest.json.JacksonWordSerializer;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "words")
@JsonSerialize(using = JacksonWordSerializer.class)
@JsonDeserialize(using = JacksonWordDeserializer.class)
public class Word extends NamedEntity{

    @ManyToOne(optional = false)
    @JoinColumn(name="lang_id", foreignKey = @ForeignKey(name = "lang_id_word"))
    private Language lang;

    public Word() {
    }

    public Word(String name, Language lang) {
        super(name);
        this.lang = lang;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", name='" + super.getName() + '\'' +
                ", lang=" + lang +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(id, word.id) &&
                Objects.equals(super.getName(), word.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
