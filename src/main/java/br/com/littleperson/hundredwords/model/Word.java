package br.com.littleperson.hundredwords.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Word {

    private Long id;
    private String name;
    private List<String> description;
}
