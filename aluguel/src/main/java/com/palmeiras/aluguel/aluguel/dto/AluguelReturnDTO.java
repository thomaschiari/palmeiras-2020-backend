package com.palmeiras.aluguel.aluguel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AluguelReturnDTO {
        private String identifier;
        private String status;
        private String cpfCorretor;
        private String cpfLocatorio;
        private String idImovel;
        private String dataAluguel;
}
