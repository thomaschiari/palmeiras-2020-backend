package com.palmeiras.aluguel.aluguel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;
    
}
