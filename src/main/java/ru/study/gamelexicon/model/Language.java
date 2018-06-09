package ru.study.gamelexicon.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.study.gamelexicon.rest.json.JacksonLanguageDeserializer;
import ru.study.gamelexicon.rest.json.JacksonLanguageSerializer;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "languages")
@JsonSerialize(using = JacksonLanguageSerializer.class)
@JsonDeserialize(using = JacksonLanguageDeserializer.class)
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Language() {
    }

    public Language(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(name, language.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
