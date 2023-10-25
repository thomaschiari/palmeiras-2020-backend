package com.palmeiras.aluguel.aluguel.dto;

import org.modelmapper.ModelMapper;

import com.palmeiras.aluguel.aluguel.Aluguel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AluguelErroReturnDTO extends AluguelReturnDTO{

    private static ModelMapper modelMapper = new ModelMapper();

    private String msgErro;
    private AluguelSuccesDTO aluguel;

    public static AluguelErroReturnDTO convert(Aluguel aluguel){
        return modelMapper.map(aluguel, AluguelErroReturnDTO.class);
    }
}

