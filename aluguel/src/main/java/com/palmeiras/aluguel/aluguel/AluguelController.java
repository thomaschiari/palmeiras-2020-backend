package com.palmeiras.aluguel.aluguel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.palmeiras.aluguel.aluguel.Enum.Status;
import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;

@Controller
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;

    public List<AluguelReturnDTO> findAlugueis(Status status, String cpfCorretor, String cpfLocatario) {

        return aluguelService.findAlugueis(status, cpfCorretor, cpfLocatario);

    }
    
}
