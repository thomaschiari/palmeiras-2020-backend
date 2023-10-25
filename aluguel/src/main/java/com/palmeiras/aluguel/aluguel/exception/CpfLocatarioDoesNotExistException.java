package com.palmeiras.aluguel.aluguel.exception;

public class CpfLocatarioDoesNotExistException extends RuntimeException{

    public CpfLocatarioDoesNotExistException() {
        super("Cpf do locatário não existe!");
    }
}
