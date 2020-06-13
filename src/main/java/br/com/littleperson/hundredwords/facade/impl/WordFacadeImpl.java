package br.com.littleperson.hundredwords.facade.impl;

import br.com.littleperson.hundredwords.business.BusinessConstants;
import br.com.littleperson.hundredwords.business.WordBusiness;
import br.com.littleperson.hundredwords.facade.WordFacade;
import br.com.littleperson.hundredwords.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * The type Word facade.
 */
@Component
public class WordFacadeImpl implements WordFacade {

    /**
     * The Word business.
     */
    @Autowired
    WordBusiness wordBusiness;

    @Override
    public List<Word> listWords() {

        return wordBusiness.generateWords(LocalDate.now(ZoneId.of(BusinessConstants.TIME_ZONE)));

    }

    @Override
    public void saveWord(Word word) {

        wordBusiness.wordSave(word);

    }

    @Override
    public Word getWord(String word) {

        return wordBusiness.getWord(word);
    }

}
