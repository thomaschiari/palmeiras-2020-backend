package com.palmeiras.aluguel.aluguel;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguelRepository extends MongoRepository<Aluguel, String> {
    
}
