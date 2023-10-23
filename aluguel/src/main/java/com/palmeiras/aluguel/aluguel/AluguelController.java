package com.palmeiras.aluguel.aluguel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;
import com.palmeiras.aluguel.aluguel.enumAluguel.Status;

@RestController
@RequestMapping("/aluguel")
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;


    @GetMapping
    public List<AluguelReturnDTO> findAlugueis(@RequestParam Status status, @RequestParam String cpfCorretor, @RequestParam String cpfLocatario) {
        return aluguelService.findAlugueis(status, cpfCorretor, cpfLocatario);

    }
    
}
