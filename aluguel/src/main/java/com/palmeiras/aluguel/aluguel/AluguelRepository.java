package com.palmeiras.aluguel.aluguel;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.palmeiras.aluguel.aluguel.enumerate.Status;

@Repository
public interface AluguelRepository extends MongoRepository<Aluguel, String> {

    List<Aluguel> findByStatus(Status status);
    List<Aluguel> findByCpfCorretor(String cpfCorretor);
    List<Aluguel> findByCpfLocatorio(String cpfLocatorio);
    List<Aluguel> findByStatusAndCpfCorretor(Status status, String cpfCorretor);
    List<Aluguel> findByStatusAndCpfLocatorio(Status status, String cpfLocatorio);
    List<Aluguel> findByCpfCorretorAndCpfLocatorio(String cpfCorretor, String cpfLocatorio);
    List<Aluguel> findByStatusAndCpfCorretorAndCpfLocatorio(Status status, String cpfCorretor, String cpfLocatorio);
    
}
