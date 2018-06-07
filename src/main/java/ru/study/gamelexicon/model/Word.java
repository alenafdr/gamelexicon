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
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name="lang_id", foreignKey = @ForeignKey(name = "lang_id_word"))
    private Language lang;

    public Word() {
    }

    public Word(String name, Language lang) {
        this.name = name;
        this.lang = lang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", name='" + name + '\'' +
                ", lang=" + lang +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(id, word.id) &&
                Objects.equals(name, word.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
