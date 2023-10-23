package com.palmeiras.aluguel.aluguel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;
import com.palmeiras.aluguel.aluguel.enumerate.Status;
import com.palmeiras.aluguel.aluguel.exception.InvalidStatusException;

@RestController
@RequestMapping("/aluguel")
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;


    @GetMapping
    public List<AluguelReturnDTO> findAlugueis(@RequestParam String status, @RequestParam String cpfCorretor, @RequestParam String cpfLocatario) {
        if (!status.equals("ERRO") && !status.equals("SUCESSO")) throw new InvalidStatusException();
        return status.equals("ERRO") ? aluguelService.findAlugueis(Status.ERRO, cpfCorretor, cpfLocatario) : aluguelService.findAlugueis(Status.SUCESSO, cpfCorretor, cpfLocatario);
    }
    
}
