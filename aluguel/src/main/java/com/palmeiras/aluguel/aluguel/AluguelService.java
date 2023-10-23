package com.palmeiras.aluguel.aluguel;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;
import com.palmeiras.aluguel.aluguel.enumerate.Status;
import com.palmeiras.aluguel.aluguel.exception.CpfCorretorDoesNotExistException;
import com.palmeiras.aluguel.aluguel.exception.CpfLocatarioDoesNotExistException;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    public List<AluguelReturnDTO> findAlugueis(Status s, String cpfCorretor, String cpfLocatario) {
        if (cpfCorretor != null && !aluguelRepository.existsByCpfCorretor(cpfCorretor)) throw new CpfCorretorDoesNotExistException();
        if (cpfLocatario != null && !aluguelRepository.existsByCpfLocatorio(cpfLocatario)) throw new CpfLocatarioDoesNotExistException();

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
