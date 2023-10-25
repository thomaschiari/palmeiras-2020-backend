package com.palmeiras.aluguel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.palmeiras.aluguel.aluguel.Aluguel;
import com.palmeiras.aluguel.aluguel.AluguelRepository;
import com.palmeiras.aluguel.aluguel.AluguelService;
import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
public class AluguelServiceTests {

    @InjectMocks
    private AluguelService aluguelService;

    @Mock
    private AluguelRepository aluguelRepository;

    @Test
    void findAlugueisTestEmpty() {
        Mockito.when(aluguelRepository.findAll()).thenReturn(new ArrayList<>());

        List<AluguelReturnDTO> alugueis = aluguelService.findAlugueis(null, null, null);

        Assertions.assertEquals(0, alugueis.size());

    }

    @Test
    void findAlugueisNotEmpty() {
        List<Aluguel> alugueis = new ArrayList<>();
        Aluguel a = new Aluguel();
        a.setCpfCorretor("123");
        alugueis.add(a);
        Mockito.when(aluguelRepository.findAll()).thenReturn(alugueis);

        List<AluguelReturnDTO> alugueisRetornados = aluguelService.findAlugueis(null, null, null);

        Assertions.assertEquals(1, alugueisRetornados.size());
    }

    @Test
    void findAlugueisByCpfCorretor() {
        List<Aluguel> alugueis = new ArrayList<>();

        Aluguel a = new Aluguel();
        a.setCpfCorretor("123");
        alugueis.add(a);

<<<<<<< HEAD
        List<AluguelReturnDTO> alugueisRetornados = aluguelService.findAlugueis(null, "123", null);
=======
        when(aluguelRepository.existsByCpfCorretor("123")).thenReturn(true);
        when(aluguelRepository.findByCpfCorretor("123")).thenReturn(alugueis);
        List<AluguelReturnDTO> alugueisRetornados = aluguelService.findAlugueis(null, "123", null);

        Assertions.assertEquals(1, alugueisRetornados.size());
    }

    @Test
    void findAlugueisByCpfLocatorio(){
        List<Aluguel> alugueis = new ArrayList<>();

        Aluguel a = new Aluguel();
        a.setCpfLocatorio("123");
        alugueis.add(a);

        when(aluguelRepository.existsByCpfLocatorio("123")).thenReturn(true);
        when(aluguelRepository.findByCpfLocatorio("123")).thenReturn(alugueis);
        List<AluguelReturnDTO> alugueisRetornados = aluguelService.findAlugueis(null, null, "123");
>>>>>>> e60af07803e9362949858ed7c8730ab4a77d380c

        Assertions.assertEquals(1, alugueisRetornados.size());
    }
    
}
