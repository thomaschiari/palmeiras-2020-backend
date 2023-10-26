package com.palmeiras.aluguel.aluguel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;
import com.palmeiras.aluguel.aluguel.dto.AluguelSaveDTO;

@RestController
@RequestMapping("/aluguel")
public class AluguelController {

    @Autowired
    private AluguelService aluguelService;


    @GetMapping
    public List<AluguelReturnDTO> findAlugueis(@RequestParam(required = false) String status, @RequestParam(required = false) String cpfCorretor, @RequestParam(required = false) String cpfLocatario, @RequestHeader(name="token") String token) {
        return aluguelService.findAlugueis(status, cpfCorretor, cpfLocatario, token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AluguelReturnDTO alugarImovel(@RequestBody AluguelSaveDTO aluguelDTO, @RequestHeader(name="token") String token) {
        return aluguelService.alugarImovel(aluguelDTO, token);
    }    
    
}
