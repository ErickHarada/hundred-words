package br.com.littleperson.hundredwords.business;

import br.com.littleperson.hundredwords.model.Word;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Word business.
 */
public interface WordBusiness {

    /**
     * Generate words list.
     *
     * @param localDate the local date
     * @return the list
     */
    List<Word> generateWords(LocalDate localDate);

    /**
     * Word save.
     *
     * @param word the word
     */
    void wordSave(Word word);

    /**
     * Gets word.
     *
     * @param word the word
     * @return the word
     */
    Word getWord(String word);

}
