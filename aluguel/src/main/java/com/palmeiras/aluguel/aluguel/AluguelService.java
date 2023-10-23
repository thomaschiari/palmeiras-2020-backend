package com.palmeiras.aluguel.aluguel;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;
import com.palmeiras.aluguel.aluguel.enumAluguel.Status;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    //Lista aluguéis: Lista todos os alugueis cadastrados, deve ser possível fazer um filtro pelo
    //status do aluguel e também pelo cpf do corretor e do locatário.

    public List<AluguelReturnDTO> findAlugueis(Status s, String cpfCorretor, String cpfLocatario) {
        List<Aluguel> alugueis;

        if (s == null && cpfCorretor == null && cpfLocatario == null) alugueis = aluguelRepository.findAll();
        else if (cpfCorretor == null && cpfLocatario == null) alugueis = aluguelRepository.findByStatus(s);
        else if (s == null && cpfCorretor == null) alugueis = aluguelRepository.findByCpfLocatorio(cpfLocatario);
        else if (s == null && cpfLocatario == null) alugueis = aluguelRepository.findByCpfCorretor(cpfCorretor);
        else if (s == null) alugueis = aluguelRepository.findByCpfCorretorAndCpfLocatorio(cpfCorretor, cpfLocatario);
        else if (cpfCorretor == null) alugueis = aluguelRepository.findByStatusAndCpfLocatorio(s, cpfLocatario);
        else if (cpfLocatario == null) alugueis = aluguelRepository.findByStatusAndCpfCorretor(s, cpfCorretor);
        else alugueis = aluguelRepository.findByStatusAndCpfCorretorAndCpfLocatorio(s, cpfCorretor, cpfLocatario);
        
        return alugueis.stream().map(alu -> AluguelReturnDTO.convert(alu)).collect(Collectors.toList());
    }
}
