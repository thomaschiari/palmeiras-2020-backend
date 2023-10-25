package com.palmeiras.aluguel.aluguel.exception;

public class CpfCorretorDoesNotExistException extends RuntimeException {
    
    public CpfCorretorDoesNotExistException() {
        super("Cpf do corretor não existe!");
    }
}
