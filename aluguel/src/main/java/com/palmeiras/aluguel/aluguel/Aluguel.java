package com.palmeiras.aluguel.aluguel;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.palmeiras.aluguel.aluguel.dto.AluguelSaveDTO;
import com.palmeiras.aluguel.aluguel.enumerate.Status;

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
    private String cpfLocatario;
    private String idImovel;
    private LocalDateTime dataAluguel;
}
