package br.com.littleperson.hundredwords.business.impl;

import br.com.littleperson.hundredwords.business.BusinessConstants;
import br.com.littleperson.hundredwords.business.WordBusiness;
import br.com.littleperson.hundredwords.model.Word;
import br.com.littleperson.hundredwords.model.request.DescriptionRequest;
import br.com.littleperson.hundredwords.model.request.WordRequest;
import br.com.littleperson.hundredwords.repository.DescriptionRepository;
import br.com.littleperson.hundredwords.repository.WordRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The type Word business.
 */
@Service
public class WordBusinessImpl implements WordBusiness {

    /**
     * The Word repository.
     */
    @Autowired
    WordRepository wordRepository;
    /**
     * The Description repository.
     */
    @Autowired
    DescriptionRepository descriptionRepository;

    private List<Long> generateIds() {
        List<Long> idList = new ArrayList<>();
        long number;

        for (int i = 0; i < BusinessConstants.QUANTITY_WORDS; i++) {

            do {
                number = 1 + new Random().nextInt(BusinessConstants.MAX_VALUE);
            } while (idList.contains(number));

            idList.add(number);
        }
        return idList;
    }

    @Override
    @Cacheable(value = "word")
    public List<Word> generateWords(LocalDate localDate) {

        List<Long> idsList = generateIds();

        List<Word> words = new ArrayList<>();

        List<DescriptionRequest> descriptionRequests = descriptionRepository.findAllByWordIdIn(idsList);

        wordRepository.findAllById(idsList).forEach(w -> {
            Word word = new Word();
            word.setId(w.getId());
            word.setName(w.getName());
            word.setDescription(descriptionRequests.stream()
                    .filter(d -> d.getWordId().equals(w.getId()))
                    .map(DescriptionRequest::getMeaning)
                    .collect(Collectors.toList()));
            words.add(word);
        });

        return words;
    }

    @Transactional
    @Override
    public void wordSave(Word word) {

        WordRequest wordRequest = new WordRequest();
        wordRequest.setName(word.getName());
        Long lastId = wordRepository.save(wordRequest).getId();

        word.getDescription().forEach(d -> {
            DescriptionRequest descriptionRequest = new DescriptionRequest();
            descriptionRequest.setMeaning(d);
            descriptionRequest.setWordId(lastId);
            descriptionRepository.save(descriptionRequest);
        });

    }

    @SneakyThrows
    @Override
    public Word getWord(String name) {

        WordRequest wordRequest = wordRepository.findByName(name);

        if(wordRequest == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<DescriptionRequest> descriptionRequests = descriptionRepository.findAllById(wordRequest.getId());

        Word word = new Word();
        word.setId(wordRequest.getId());
        word.setName(wordRequest.getName());
        word.setDescription(descriptionRequests.stream().map(d -> d.getMeaning()).collect(Collectors.toList()));

        return word;
    }
}
