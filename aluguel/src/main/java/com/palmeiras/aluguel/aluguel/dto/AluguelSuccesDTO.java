package com.palmeiras.aluguel.aluguel.dto;

import org.modelmapper.ModelMapper;
import com.palmeiras.aluguel.aluguel.Aluguel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AluguelSuccesDTO extends AluguelReturnDTO{

    private static ModelMapper modelMapper = new ModelMapper();

    private String identifier;
    private String status;
    private String cpfCorretor;
    private String cpfLocatario;
    private String idImovel;
    private String dataAluguel;

    public static AluguelSuccesDTO convert(Aluguel aluguel){
        return modelMapper.map(aluguel, AluguelSuccesDTO.class);
    }
}
