package com.palmeiras.aluguel.aluguel;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.palmeiras.aluguel.aluguel.Enum.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("aluguel")
public class Aluguel {

    @Id
    private String id;
    private String identifier;
    private Status status;
    private String cpfCorretor;
    private String cpfLocatorio;
    private String idImovel;
    private LocalDateTime dataAluguel;
    
}
