package com.palmeiras.aluguel.aluguel.dto;

import org.modelmapper.ModelMapper;

import com.palmeiras.aluguel.aluguel.Aluguel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AluguelSaveDTO {
    
    private static ModelMapper modelMapper = new ModelMapper();

    private String cpfCorretor;
    private String cpfLocatario;
    private String idImovel;
    
    public static AluguelSaveDTO convert(Aluguel aluguel){
        return modelMapper.map(aluguel, AluguelSaveDTO.class);
    }
}
