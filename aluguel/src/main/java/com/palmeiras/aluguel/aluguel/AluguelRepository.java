package com.palmeiras.aluguel.aluguel;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.palmeiras.aluguel.aluguel.enumerate.Status;

@Repository
public interface AluguelRepository extends MongoRepository<Aluguel, String> {

    boolean existsByCpfCorretor(String cpfCorretor);
    boolean existsByCpfLocatario(String cpfLocatario);
    
    List<Aluguel> findByStatus(Status status);
    List<Aluguel> findByCpfCorretor(String cpfCorretor);
    List<Aluguel> findByCpfLocatario(String cpfLocatario);
    List<Aluguel> findByStatusAndCpfCorretor(Status status, String cpfCorretor);
    List<Aluguel> findByStatusAndCpfLocatario(Status status, String cpfLocatario);
    List<Aluguel> findByCpfCorretorAndCpfLocatario(String cpfCorretor, String cpfLocatario);
    List<Aluguel> findByStatusAndCpfCorretorAndCpfLocatario(Status status, String cpfCorretor, String cpfLocatario);
    Aluguel findByIdentifier(String identifier);
    
}
