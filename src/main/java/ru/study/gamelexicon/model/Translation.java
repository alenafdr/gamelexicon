package ru.study.gamelexicon.model;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Entity
@Table(name = "translations")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "id_word_origin")
    private Word origin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "word_origin_translation", joinColumns = @JoinColumn(name = "origin_id"),
            inverseJoinColumns = @JoinColumn(name = "translation_id"))
    private Set<Word> translations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Word getOrigin() {
        return origin;
    }

    public void setOrigin(Word origin) {
        this.origin = origin;
    }

    public Set<Word> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Word> translations) {
        this.translations = translations;
    }

    public Word getTranslationByLang(Language language){
        Stream<Word> wordStream = translations.stream().filter((Word word) -> word.getLang().getName().equals(language.getName()));
        Optional<Word> optional = wordStream.findFirst();
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Translations{" +
                "id=" + id +
                ", origin=" + origin +
                ", translations=" + translations +
                '}';
    }
}
