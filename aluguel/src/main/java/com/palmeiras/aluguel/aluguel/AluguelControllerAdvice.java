package com.palmeiras.aluguel.aluguel;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.palmeiras.aluguel.aluguel.exception.CpfCorretorDoesNotExistException;
import com.palmeiras.aluguel.aluguel.exception.CpfLocatarioDoesNotExistException;
import com.palmeiras.aluguel.aluguel.exception.InvalidStatusException;
import com.palmeiras.aluguel.common.ExceptionDTO;

@ControllerAdvice
public class AluguelControllerAdvice {
    
    @ExceptionHandler(CpfCorretorDoesNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleAluguelNotFoundException(CpfCorretorDoesNotExistException e) {
        return new ExceptionDTO(e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
    }

    @ExceptionHandler(CpfLocatarioDoesNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleAluguelNotFoundException(CpfLocatarioDoesNotExistException e) {
        return new ExceptionDTO(e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
    }

    @ExceptionHandler(InvalidStatusException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO handleAluguelNotFoundException(InvalidStatusException e) {
        return new ExceptionDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }
}
