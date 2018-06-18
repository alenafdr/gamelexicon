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
public class Language extends NamedEntity{

    public Language() {
    }

    public Language(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + super.getName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(super.getName(), language.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.getName());
    }
}
