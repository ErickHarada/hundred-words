package br.com.littleperson.hundredwords.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "description")
public class DescriptionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_description")
    private Long id;
    @Column(name = "meaning")
    private String meaning;
    @Column(name = "fk_word_id_word")
    private Long wordId;
}
