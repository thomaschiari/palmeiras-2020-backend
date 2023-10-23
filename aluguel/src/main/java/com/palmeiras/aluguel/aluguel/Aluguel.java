package com.palmeiras.aluguel.aluguel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("aluguel")
public class Aluguel {

    @Id
    private String id;
    private String identifier;
    private String status;
    private String cpfCorretor;
    private String cpfLocatorio;
    
}
