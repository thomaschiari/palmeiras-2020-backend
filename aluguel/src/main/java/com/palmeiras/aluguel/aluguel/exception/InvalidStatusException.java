package com.palmeiras.aluguel.aluguel.exception;

public class InvalidStatusException extends RuntimeException {
    
    public InvalidStatusException() {
        super("Status inválido!");
    }
}
