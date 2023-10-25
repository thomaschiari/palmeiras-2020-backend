package com.palmeiras.aluguel.common;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionDTO {

    private String message;
    private Integer code;
    private LocalDateTime time;

    public ExceptionDTO(String message, Integer code, LocalDateTime time) {
        this.message = message;
        this.code = code;
        this.time = time;
    }
}
