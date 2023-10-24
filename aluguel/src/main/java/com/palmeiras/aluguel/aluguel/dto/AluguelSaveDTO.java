package com.palmeiras.aluguel.aluguel.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.palmeiras.aluguel.aluguel.Aluguel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AluguelSaveDTO {
    
    @Autowired
    private static ModelMapper modelMapper;

    private String cpfCorretor;
    private String cpfLocatario;
    private String idImovel;
    
    public static AluguelSaveDTO convert(Aluguel aluguel){
        return modelMapper.map(aluguel, AluguelSaveDTO.class);
    }
}
