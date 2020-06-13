package br.com.littleperson.hundredwords.controller;

import br.com.littleperson.hundredwords.facade.WordFacade;
import br.com.littleperson.hundredwords.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Words controller.
 */
@RestController
@RequestMapping("/cem-palavras")
public class WordsController {

    /**
     * The Word facade.
     */
    @Autowired
    WordFacade wordFacade;

    /**
     * Hundred words list.
     *
     * @return the list
     */
    @GetMapping
    public List<Word> hundredWords() {

        return wordFacade.listWords();
    }

    /**
     * Insert word.
     *
     * @param word the word
     */
    @PostMapping
    public void insertWord(@Valid @RequestBody Word word) {

        wordFacade.saveWord(word);
    }

    /**
     * Gets word.
     *
     * @param word the word
     * @return the word
     */
    @GetMapping("/{word}")
    public Word getWord(@Valid @PathVariable String word) {

        return wordFacade.getWord(word);
    }
}
