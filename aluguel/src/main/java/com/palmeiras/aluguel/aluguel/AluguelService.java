package com.palmeiras.aluguel.aluguel;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;
import com.palmeiras.aluguel.aluguel.dto.AluguelSaveDTO;
import com.palmeiras.aluguel.aluguel.enumerate.Status;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    /*
    Lista aluguéis: Lista todos os alugueis cadastrados, deve ser possível fazer um filtro pelo
    status do aluguel e também pelo cpf do corretor e do locatário. 
    */

    public List<AluguelReturnDTO> findAlugueis(Status s, String cpfCorretor, String cpfLocatario) {
        List<Aluguel> alugueis;

        if (s == null && cpfCorretor == null && cpfLocatario == null) alugueis = aluguelRepository.findAll();
        else if (cpfCorretor == null && cpfLocatario == null) alugueis = aluguelRepository.findByStatus(s);
        else if (s == null && cpfCorretor == null) alugueis = aluguelRepository.findByCpfLocatorio(cpfLocatario);
        else if (s == null && cpfLocatario == null) alugueis = aluguelRepository.findByCpfCorretor(cpfCorretor);
        else if (s == null) alugueis = aluguelRepository.findByCpfCorretorAndCpfLocatorio(cpfCorretor, cpfLocatario);
        else if (cpfCorretor == null) alugueis = aluguelRepository.findByStatusAndCpfLocatorio(s, cpfLocatario);
        else if (cpfLocatario == null) alugueis = aluguelRepository.findByStatusAndCpfCorretor(s, cpfCorretor);
        else alugueis = aluguelRepository.findByStatusAndCpfCorretorAndCpfLocatorio(s, cpfCorretor, cpfLocatario);
        
        return alugueis.stream().map(alu -> AluguelReturnDTO.convert(alu)).collect(Collectors.toList());
    }

    public AluguelReturnDTO alugarImovel(AluguelSaveDTO aluguelDTO) {
        /* 
            Essa rota deve cadastrar um novo aluguel, ela deve receber o cpf de um
            cliente (locatorio), o cpf de um corretor e o identificador de um imóvel. Ela deve validar se o cliente e o
            corretor existem nos respectivos serviços, se sim, ela deve chamar a rota que aluga/vende para
            mudar o status do imóvel. Se tudo der certo ela deve salvar o aluguel com o status SUCESSO.
            Se alguma das validações derem errado, ela deve salvar o aluguel com status ERRO.
        */
        
        Boolean erro = false;

        // Valida se o cliente existe (Listagem de clientes:), se n existir erro = True

        // Valida se o corretor existe (Listagem de corretores), se n existir erro = True

        // Valida se o imóvel existe, e se está disponivel, se n existir ou n disponivel erro = True 
            /*
            Aluga ou Vende Imóvel: Recebe um identificador de um imóvel e o status indicando se o imóvel
            deve ser alugado ou vendido. Se o imóvel estiver disponível deve mudar o status do imóvel e
            retornar sucesso. Se o imóvel não existir deve retornar um erro 404, se ele já estiver alugado
            ou vendido deve retornar um erro 400.
            */

        Aluguel aluguel = new Aluguel();

        if (erro) {

            // Salva o aluguel com status ERRO

            aluguel.setCpfCorretor(aluguelDTO.getCpfCorretor());
            aluguel.setCpfLocatorio(aluguelDTO.getCpfLocatario());
            aluguel.setIdImovel(aluguelDTO.getIdImovel());
            aluguel.setStatus(Status.ERRO);
            aluguel.setDataAluguel(LocalDateTime.now());
            aluguel.setIdentifier(UUID.randomUUID().toString());



        } else {

            // Salva o aluguel com status SUCESSO

            aluguel.setCpfCorretor(aluguelDTO.getCpfCorretor());
            aluguel.setCpfLocatorio(aluguelDTO.getCpfLocatario());
            aluguel.setIdImovel(aluguelDTO.getIdImovel());
            aluguel.setStatus(Status.SUCESSO);
            aluguel.setDataAluguel(LocalDateTime.now());
            aluguel.setIdentifier(UUID.randomUUID().toString());
        }

        aluguelRepository.save(aluguel);

        /* 
        Duvidas da rota:
        se der erro continua mesmo salvando então como erro?
        se um imóvel for salvo em um aluguél com erro ele continua disponivel ou não?
        se não existir o corretor ou cliente deve retornar erro ou salva aluguel com status erro?
        */

        return AluguelReturnDTO.convert(aluguel);
    }
}
