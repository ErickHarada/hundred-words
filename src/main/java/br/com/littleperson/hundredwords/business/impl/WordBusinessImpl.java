package br.com.littleperson.hundredwords.business.impl;

import br.com.littleperson.hundredwords.business.BusinessConstants;
import br.com.littleperson.hundredwords.business.WordBusiness;
import br.com.littleperson.hundredwords.model.Word;
import br.com.littleperson.hundredwords.model.request.DescriptionRequest;
import br.com.littleperson.hundredwords.model.request.WordRequest;
import br.com.littleperson.hundredwords.repository.DescriptionRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The type Word business.
 */
@Service
public class WordBusinessImpl implements WordBusiness {

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

        List<DescriptionRequest> descriptionRequests = descriptionRepository.findAllByWordId_IdIn(idsList);

        List<WordRequest> listWord = descriptionRequests.stream().map(DescriptionRequest::getWordId)
                .distinct().collect(Collectors.toList());

        listWord.forEach(w -> {
            Word word = new Word();
            word.setId(w.getId());
            word.setName(w.getName());
            word.setDescription(descriptionRequests.stream()
                    .filter(d -> d.getWordId().getId().equals(w.getId()))
                    .map(DescriptionRequest::getMeaning)
                    .collect(Collectors.toList()));
            words.add(word);
        });

        return words;
    }

    @Transactional
    @Override
    public void wordSave(Word word) {

        WordRequest wordRequest = WordRequest.builder()
                .name(word.getName())
                .build();

        word.getDescription().forEach(d -> {
            DescriptionRequest descriptionRequest = DescriptionRequest.builder()
                    .meaning(d)
                    .wordId(wordRequest)
                    .build();
            descriptionRepository.save(descriptionRequest);
        });
    }

    @SneakyThrows
    @Override
    public Word getWord(String name) {
        List<DescriptionRequest> descriptionRequests = descriptionRepository.findAllByWordId_Name(name);

        if (descriptionRequests.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Word word = new Word();
        word.setId(descriptionRequests.get(0).getWordId().getId());
        word.setName(descriptionRequests.get(0).getWordId().getName());
        word.setDescription(descriptionRequests.stream().map(d -> d.getMeaning()).collect(Collectors.toList()));

        return word;
    }
}
