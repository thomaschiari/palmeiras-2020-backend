package com.palmeiras.aluguel.aluguel.dto;

import org.modelmapper.ModelMapper;
import com.palmeiras.aluguel.aluguel.Aluguel;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AluguelReturnDTO {

    @Autowired
    private static ModelMapper modelMapper;
    private String identifier;
    private String status;
    private String cpfCorretor;
    private String cpfLocatorio;
    private String idImovel;
    private String dataAluguel;

    public static AluguelReturnDTO convert(Aluguel aluguel){
        return modelMapper.map(aluguel, AluguelReturnDTO.class);
    }
}
