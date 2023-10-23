package com.palmeiras.aluguel.aluguel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.palmeiras.aluguel.aluguel.Enum.Status;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    //Lista aluguéis: Lista todos os alugueis cadastrados, deve ser possível fazer um filtro pelo
    //status do aluguel e também pelo cpf do corretor e do locatário.

    public List<Aluguel> findAlugueis(Status s, String cpfCorretor, String cpfLocatario) {

        if (s == null && cpfCorretor == null && cpfLocatario == null) return aluguelRepository.findAll();
        else if (cpfCorretor == null && cpfLocatario == null) return aluguelRepository.findByStatus(s);
        else if (s == null && cpfCorretor == null) return aluguelRepository.findByCpfLocatorio(cpfLocatario);
        else if (s == null && cpfLocatario == null) return aluguelRepository.findByCpfCorretor(cpfCorretor);
        else if (s == null) return aluguelRepository.findByCpfCorretorAndCpfLocatorio(cpfCorretor, cpfLocatario);
        else if (cpfCorretor == null) return aluguelRepository.findByStatusAndCpfLocatorio(s, cpfLocatario);
        else if (cpfLocatario == null) return aluguelRepository.findByStatusAndCpfCorretor(s, cpfCorretor);
        else return aluguelRepository.findByStatusAndCpfCorretorAndCpfLocatorio(s, cpfCorretor, cpfLocatario);

    }
    
}
