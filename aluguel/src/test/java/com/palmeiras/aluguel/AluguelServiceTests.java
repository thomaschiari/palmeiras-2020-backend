package com.palmeiras.aluguel;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.palmeiras.aluguel.aluguel.enumerate.Status;
import com.palmeiras.aluguel.aluguel.exception.CpfCorretorDoesNotExistException;
import com.palmeiras.aluguel.aluguel.exception.CpfLocatarioDoesNotExistException;
import com.palmeiras.aluguel.aluguel.exception.InvalidStatusException;

import org.junit.jupiter.api.Assertions;

@ExtendWith(MockitoExtension.class)
public class AluguelServiceTests {

    @InjectMocks
    private AluguelService aluguelService;

    @Mock
    private AluguelRepository aluguelRepository;

    /*
    @Test
    void findAlugueisTestEmpty() {
        Mockito.when(aluguelRepository.findAll()).thenReturn(new ArrayList<>());
        List<AluguelReturnDTO> alugueis = aluguelService.findAlugueis(null, null, null, null);
        Assertions.assertEquals(0, alugueis.size());
    }

    @Test
    void findAlugueisNotEmpty() {
        List<Aluguel> alugueis = new ArrayList<>();
        Aluguel a = new Aluguel();
        a.setCpfCorretor("123");
        alugueis.add(a);
        Mockito.when(aluguelRepository.findAll()).thenReturn(alugueis);

        List<AluguelReturnDTO> alugueisRetornados = aluguelService.findAlugueis(null, null, null, null);
        Assertions.assertEquals(1, alugueisRetornados.size());
    }

    @Test
    void findAluguelByAllFilters() {
        List<Aluguel> alugueis = new ArrayList<>();
        Aluguel a = new Aluguel();

        a.setCpfCorretor("1230");
        a.setCpfLocatario("3030");
        alugueis.add(a);

        Mockito.when(aluguelRepository.existsByCpfCorretor("1230")).thenReturn(true);
        Mockito.when(aluguelRepository.existsByCpfLocatario("3030")).thenReturn(true);

        Mockito.when(aluguelRepository.findByStatusAndCpfCorretorAndCpfLocatario(Status.SUCESSO, "1230","3030")).thenReturn(alugueis);
        List<AluguelReturnDTO> alugueisRetornados = aluguelService.findAlugueis("sucesso", "1230","3030", null);
        Assertions.assertEquals(1, alugueisRetornados.size());
    }

    @Test
    void findAlugueisByCpfCorretor() {
        List<Aluguel> alugueis = new ArrayList<>();

        Aluguel a = new Aluguel();
        a.setCpfCorretor("123");
        alugueis.add(a);

        Mockito.when(aluguelRepository.existsByCpfCorretor("123")).thenReturn(true);
        Mockito.when(aluguelRepository.findByCpfCorretor("123")).thenReturn(alugueis);
        List<AluguelReturnDTO> alugueisRetornados = aluguelService.findAlugueis(null, "123", null, null);

        Assertions.assertEquals(1, alugueisRetornados.size());
    }

    @Test
    void findAlugueisByCpfLocatario(){
        List<Aluguel> alugueis = new ArrayList<>();

        Aluguel a = new Aluguel();
        a.setCpfLocatario("123");
        alugueis.add(a);

        Mockito.when(aluguelRepository.existsByCpfLocatario("123")).thenReturn(true);
        Mockito.when(aluguelRepository.findByCpfLocatario("123")).thenReturn(alugueis);
        List<AluguelReturnDTO> alugueisRetornados = aluguelService.findAlugueis(null, null, "123", null);

        Assertions.assertEquals(1, alugueisRetornados.size());
    }

    @Test 
    void invalidAluguelStatus(){
        assertThrows(InvalidStatusException.class, () -> {
            aluguelService.findAlugueis("StatusInvalido", null, null, null);
        });
    }

    @Test
    void testCpfCorretorDoesNotExistException() {
        Mockito.when(aluguelRepository.existsByCpfCorretor("123")).thenReturn(false);
        assertThrows(CpfCorretorDoesNotExistException.class, () -> {
            aluguelService.findAlugueis(null, "123", null, null);
        });
    }

    @Test
    void testCpfLocatarioDoesNotExistException() {
        Mockito.when(aluguelRepository.existsByCpfLocatario("123")).thenReturn(false);
        assertThrows(CpfLocatarioDoesNotExistException.class, () -> {
            aluguelService.findAlugueis(null, null, "123", null);
        });
    }
    */
}
