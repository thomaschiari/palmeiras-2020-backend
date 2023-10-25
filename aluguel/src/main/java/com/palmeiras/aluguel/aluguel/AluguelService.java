package com.palmeiras.aluguel.aluguel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.palmeiras.aluguel.aluguel.dto.AluguelErroReturnDTO;
import com.palmeiras.aluguel.aluguel.dto.AluguelReturnDTO;
import com.palmeiras.aluguel.aluguel.dto.AluguelSuccesDTO;
import com.palmeiras.aluguel.aluguel.dto.AluguelSaveDTO;
import com.palmeiras.aluguel.aluguel.enumerate.Status;
import com.palmeiras.aluguel.aluguel.exception.CpfCorretorDoesNotExistException;
import com.palmeiras.aluguel.aluguel.exception.CpfLocatarioDoesNotExistException;
import com.palmeiras.aluguel.aluguel.exception.InvalidStatusException;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    public List<AluguelReturnDTO> findAlugueis(String aluguelStatus, String cpfCorretor, String cpfLocatario) {
        Status s = null;

        if(aluguelStatus != null){
            if(aluguelStatus.equals("ERRO") || aluguelStatus.equals("SUCESSO")){
                s = Status.valueOf(aluguelStatus);
            } else {
                throw new InvalidStatusException();
            }
        }

        if (cpfCorretor != null && !aluguelRepository.existsByCpfCorretor(cpfCorretor)) throw new CpfCorretorDoesNotExistException();
        if (cpfLocatario != null && !aluguelRepository.existsByCpfLocatorio(cpfLocatario)) throw new CpfLocatarioDoesNotExistException();

        List<Aluguel> alugueis;

        if (s == null && cpfCorretor == null && cpfLocatario == null) alugueis = aluguelRepository.findAll();
        else if (cpfCorretor == null && cpfLocatario == null) alugueis = aluguelRepository.findByStatus(s);
        else if (s == null && cpfCorretor == null) alugueis = aluguelRepository.findByCpfLocatorio(cpfLocatario);
        else if (s == null && cpfLocatario == null) alugueis = aluguelRepository.findByCpfCorretor(cpfCorretor);
        else if (s == null) alugueis = aluguelRepository.findByCpfCorretorAndCpfLocatorio(cpfCorretor, cpfLocatario);
        else if (cpfCorretor == null) alugueis = aluguelRepository.findByStatusAndCpfLocatorio(s, cpfLocatario);
        else if (cpfLocatario == null) alugueis = aluguelRepository.findByStatusAndCpfCorretor(s, cpfCorretor);
        else alugueis = aluguelRepository.findByStatusAndCpfCorretorAndCpfLocatorio(s, cpfCorretor, cpfLocatario);
        
        return alugueis.stream().map(alu -> AluguelSuccesDTO.convert(alu)).collect(Collectors.toList());
    }

    public AluguelReturnDTO alugarImovel(AluguelSaveDTO aluguelDTO) {
        /* 
            Essa rota deve cadastrar um novo aluguel, ela deve receber o cpf de um
            cliente (locatorio), o cpf de um corretor e o identificador de um imóvel. Ela deve validar se o cliente e o
            corretor existem nos respectivos serviços, se sim, ela deve chamar a rota que aluga/vende para
            mudar o status do imóvel. Se tudo der certo ela deve salvar o aluguel com o status SUCESSO.
            Se alguma das validações derem errado, ela deve salvar o aluguel com status ERRO.
        */
        String msg = "";
        Boolean erro = false;
        ResponseEntity<Void> response;
        RestTemplate restTemplate = new RestTemplate();

        response = restTemplate.getForEntity("http://localhost:8080/cliente/" + aluguelDTO.getCpfLocatario(), null);
        if(!response.getStatusCode().is2xxSuccessful()){
            msg += "Cliente não encontrado.\n";
            erro = true;
        }

        restTemplate = new RestTemplate();
        response = restTemplate.getForEntity("http://localhost:8080/corretor/" + aluguelDTO.getCpfCorretor(), null);
        if(!response.getStatusCode().is2xxSuccessful()){
            msg += "Corretor não encontrado.\n";
            erro = true;
        }
        
        Aluguel aluguel = new Aluguel();
        
        aluguel.convertSaveDTO(aluguelDTO);
        aluguel.setDataAluguel(LocalDateTime.now());
        aluguel.setIdentifier(UUID.randomUUID().toString());

        if(!erro){
            restTemplate = new RestTemplate();
            response = restTemplate.getForEntity("http://localhost:8080/imovel/aluga" + aluguelDTO.getIdImovel(), null);
            if(response.getStatusCode().is2xxSuccessful()){
                aluguel.setStatus(Status.SUCESSO);
            } else {
                if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                    msg += "Imóvel não encontrado.\n";
                } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    msg += "Imóvel já alugado.\n";
                }
                aluguel.setStatus(Status.ERRO);
            }
        } else {
            aluguel.setStatus(Status.ERRO);
        }

        // Valida se o cliente existe (Listagem de clientes:), se n existir erro = True

        // Valida se o corretor existe (Listagem de corretores), se n existir erro = True

        // Valida se o imóvel existe, e se está disponivel, se n existir ou n disponivel erro = True 
            /*
            Aluga ou Vende Imóvel: Recebe um identificador de um imóvel e o status indicando se o imóvel
            deve ser alugado ou vendido. Se o imóvel estiver disponível deve mudar o status do imóvel e
            retornar sucesso. Se o imóvel não existir deve retornar um erro 404, se ele já estiver alugado
            ou vendido deve retornar um erro 400.
            */

        aluguelRepository.save(aluguel);

        /* 
        Duvidas da rota:
        se der erro continua mesmo salvando então como erro?
        se um imóvel for salvo em um aluguél com erro ele continua disponivel ou não?
        se não existir o corretor ou cliente deve retornar erro ou salva aluguel com status erro?
        */

        if (erro) {
            AluguelErroReturnDTO erroDTO = new AluguelErroReturnDTO();
            erroDTO.setAluguel(AluguelSuccesDTO.convert(aluguel));
            erroDTO.setMsgErro(msg);
            return erroDTO;
        }

        return AluguelSuccesDTO.convert(aluguel);
    }
}
