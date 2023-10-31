package com.palmeiras.aluguel.aluguel.exception;

public class AluguelDoesNotExistException extends RuntimeException {

    public AluguelDoesNotExistException() {
        super("Esse aluguel não existe!");
    }
}
