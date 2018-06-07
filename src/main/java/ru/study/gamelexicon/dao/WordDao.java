package ru.study.gamelexicon.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.study.gamelexicon.model.Word;

import java.util.List;

public interface WordDao extends JpaRepository<Word, Long> {
    public Word getByName(String name);

        @Query(value =  "SELECT * FROM words w " +
                        "   INNER JOIN languages l ON w.lang_id = l.id " +
                        "WHERE l.name = :strLang " +
                        "ORDER BY RAND() " +
                        "LIMIT :limit", nativeQuery = true)
    public List<Word> getByLangRandom(@Param("strLang") String strLang,
                                      @Param("limit") int limit);

    @Query(value =  "SELECT w1.id, w1.name, w1.lang_id, l1.id, l1.name, SUM(s.result_answer) as sum " +
                    "FROM words w1  " +
                    "   INNER JOIN languages l1 ON w1.lang_id = l1.id  " +
                    "   LEFT JOIN steps AS s ON w1.id = s.word_question_id " +
                    "   INNER JOIN translations AS t ON w1.id = t.id_word_origin " +
                    "   INNER JOIN word_origin_translation AS wot ON t.id = wot.origin_id " +
                    "   INNER JOIN words AS w2 ON wot.translation_id = w2.id " +
                    "   INNER JOIN languages l2 ON w2.lang_id = l2.id " +
                    "WHERE l1.name = :langQ AND l2.name = :langA " +
                    "GROUP BY w1.id, w1.name, w1.lang_id, l1.id, l1.name " +
                    "HAVING sum < :amountRepeat OR sum IS NULL " +
                    "ORDER BY RAND() " +
                    "LIMIT :limit", nativeQuery = true)
    public List<Word> getWordsForSteps(@Param("langQ") String langQ,
                                       @Param("langA") String langA,
                                       @Param("amountRepeat") int amountRepeat,
                                       @Param("limit") int limit);

    @Query(nativeQuery = true,
    value = "SELECT id, w_name, lang_id, l_id, l_name, AVG(diff) AS avg " +
            "FROM (SELECT TIMESTAMPDIFF(SECOND, steps.time_give ,steps.time_received) AS diff,  " +
                    "words.id AS id,  " +
                    "words.name AS w_name,  " +
                    "words.lang_id AS lang_id,  " +
                    "languages.id AS l_id,  " +
                    "languages.name AS l_name " +
                    "FROM users " +
                    "INNER JOIN games ON users.id = games.user_id " +
                    "INNER JOIN steps ON games.id = steps.game_id " +
                    "INNER JOIN words ON steps.word_question_id = words.id " +
                    "INNER JOIN languages ON words.lang_id = languages.id " +
                    "WHERE users.id = :userId AND steps.time_give IS NOT NULL AND steps.time_received IS NOT NULL) as a " +
            "GROUP BY id, w_name, lang_id, l_id, l_name " +
            "ORDER BY avg " +
            "LIMIT :limit")
    public List<Word> getWordsGuessedAboveThresholdByUserId(@Param("userId") Long userId,
                                                            @Param("limit") int limit);

    @Query(nativeQuery = true,
    value = "SELECT COUNT(words.id) AS count_m, words.id , " +
            "       words.name , " +
            "       words.lang_id , " +
            "       languages.id , " +
            "       languages.name " +
            "FROM users " +
            "INNER JOIN games ON users.id = games.user_id " +
            "INNER JOIN steps ON games.id = steps.game_id " +
            "INNER JOIN words ON steps.word_question_id = words.id " +
            "INNER JOIN languages ON words.lang_id = languages.id " +
            "WHERE users.id = :userId AND steps.time_give IS NOT NULL AND steps.time_received IS NOT NULL " +
            "GROUP BY words.id " +
            "ORDER BY count_m DESC " +
            "LIMIT :limit")
    public List<Word> getMostHitsByUserId(@Param("userId") Long userId,
                                          @Param("limit") int limit);

    @Query(nativeQuery = true,
    value = "SELECT COUNT(words.id) AS count_m, words.id , \n" +
            "       words.name ,  \n" +
            "       words.lang_id ,  \n" +
            "       languages.id ,  \n" +
            "       languages.name  \n" +
            "FROM users \n" +
            "INNER JOIN games ON users.id = games.user_id \n" +
            "INNER JOIN steps ON games.id = steps.game_id \n" +
            "INNER JOIN words ON steps.word_question_id = words.id \n" +
            "INNER JOIN languages ON words.lang_id = languages.id \n" +
            "WHERE users.id = :userId AND steps.result_answer = 0 \n" +
            "GROUP BY words.id \n" +
            "ORDER BY count_m DESC \n" +
            "LIMIT :limit")
    public List<Word> getMostMissesByUserId(@Param("userId") Long userId,
                                            @Param("limit") int limit);
}
