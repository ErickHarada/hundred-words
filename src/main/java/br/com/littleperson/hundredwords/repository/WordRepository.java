package br.com.littleperson.hundredwords.repository;

import br.com.littleperson.hundredwords.model.Word;
import br.com.littleperson.hundredwords.model.request.WordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WordRepository extends JpaRepository<WordRequest, Long> {

    Long findTopByOrderByIdDesc();

    WordRequest findByName(String word);
}
