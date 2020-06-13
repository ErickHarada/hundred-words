package br.com.littleperson.hundredwords.facade;

import br.com.littleperson.hundredwords.model.Word;

import java.util.List;

/**
 * The interface Word facade.
 */
public interface WordFacade {

    /**
     * List words list.
     *
     * @return the list
     */
    List<Word> listWords();

    /**
     * Save word.
     *
     * @param word the word
     */
    void saveWord(Word word);

    /**
     * Gets word.
     *
     * @param word the word
     * @return the word
     */
    Word getWord(String word);
}
