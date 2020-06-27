package br.com.littleperson.hundredwords.repository;

import br.com.littleperson.hundredwords.model.request.DescriptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepository extends JpaRepository<DescriptionRequest, Long> {

    List<DescriptionRequest> findAllByWordId_IdIn(List<Long> fkId);

    List<DescriptionRequest> findAllByWordId_Name(String name);

}
