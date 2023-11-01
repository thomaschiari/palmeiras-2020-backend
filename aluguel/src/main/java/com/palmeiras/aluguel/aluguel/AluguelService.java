package com.palmeiras.aluguel.aluguel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.palmeiras.aluguel.aluguel.exception.AluguelDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

    public List<AluguelReturnDTO> findAlugueis(String aluguelStatus, String cpfCorretor, String cpfLocatario, String token) {
        Status s = null;
        if(aluguelStatus != null){
            aluguelStatus = aluguelStatus.toUpperCase();
            if(aluguelStatus.equals("ERRO") || aluguelStatus.equals("SUCESSO")){
                s = Status.valueOf(aluguelStatus);
            } else {
                throw new InvalidStatusException();
            }
        }

        if (cpfCorretor != null || cpfLocatario != null) {
            ResponseEntity<Void> response;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            if (cpfLocatario != null) {
                try {
                    response = restTemplate.getForEntity("http://34.210.87.17:8080/cliente/exists/" + cpfLocatario, null, entity);
                } catch (Exception e) {
                    throw new CpfLocatarioDoesNotExistException();
                }
            }

            
            if (cpfCorretor != null) {
                try {
                    response = restTemplate.getForEntity("http://35.87.155.27:8080/corretor/" + cpfCorretor, null, entity);
                } catch (Exception e) {
                    throw new CpfCorretorDoesNotExistException();
                }
            }

        }

        List<Aluguel> alugueis;

        if (s == null && cpfCorretor == null && cpfLocatario == null) alugueis = aluguelRepository.findAll();
        else if (cpfCorretor == null && cpfLocatario == null) alugueis = aluguelRepository.findByStatus(s);
        else if (s == null && cpfCorretor == null) alugueis = aluguelRepository.findByCpfLocatario(cpfLocatario);
        else if (s == null && cpfLocatario == null) alugueis = aluguelRepository.findByCpfCorretor(cpfCorretor);
        else if (s == null) alugueis = aluguelRepository.findByCpfCorretorAndCpfLocatario(cpfCorretor, cpfLocatario);
        else if (cpfCorretor == null) alugueis = aluguelRepository.findByStatusAndCpfLocatario(s, cpfLocatario);
        else if (cpfLocatario == null) alugueis = aluguelRepository.findByStatusAndCpfCorretor(s, cpfCorretor);
        else alugueis = aluguelRepository.findByStatusAndCpfCorretorAndCpfLocatario(s, cpfCorretor, cpfLocatario);

        
        return alugueis.stream().map(alu -> AluguelSuccesDTO.convert(alu)).collect(Collectors.toList());
    }

    public AluguelReturnDTO alugarImovel(AluguelSaveDTO aluguelDTO, String token) {
        /* 
            Essa rota deve cadastrar um novo aluguel, ela deve receber o cpf de um
            cliente (locatorio), o cpf de um corretor e o identificador de um imóvel. Ela deve validar se o cliente e o
            corretor existem nos respectivos serviços, se sim, ela deve chamar a rota que aluga/vende para
            mudar o status do imóvel. Se tudo der certo ela deve salvar o aluguel com o status SUCESSO.
            Se alguma das validações derem errado, ela deve salvar o aluguel com status ERRO.
        */
        Boolean erro = false, erroCliente = false, erroCorretor = false, erroImovelNEncontrado = false, erroImovelAlugado = false;
        ResponseEntity<Void> response;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        try {
            response = restTemplate.getForEntity("http://34.210.87.17:8080/clientes/exists/" + aluguelDTO.getCpfLocatario(), null, entity);
        } catch (Exception e) {
            erro = true;
            erroCliente = true;
            response = null;
        }

        restTemplate = new RestTemplate();
        try {
            response = restTemplate.getForEntity("http://35.87.155.27:8080/corretor/" + aluguelDTO.getCpfCorretor(), null, entity);
        } catch (Exception e) {
            erro = true;
            erroCorretor = true;
            response = null;
        }
        
        Aluguel aluguel = new Aluguel();
        aluguel.setCpfCorretor(aluguelDTO.getCpfCorretor());
        aluguel.setCpfLocatario(aluguelDTO.getCpfLocatario());
        aluguel.setIdImovel(aluguelDTO.getIdImovel());
        aluguel.setDataAluguel(LocalDateTime.now());
        aluguel.setIdentifier(UUID.randomUUID().toString());

        if(!erro){
            restTemplate = new RestTemplate();
            try {
                response = restTemplate.exchange("http://3.16.37.117:8080/imovel/transaction/" + aluguelDTO.getIdImovel(), HttpMethod.PUT, entity, Void.class);
            } catch (HttpClientErrorException e) {
                response = new ResponseEntity<>(e.getStatusCode());
                erro = true;
            }

            if(response.getStatusCode().is2xxSuccessful()){
                aluguel.setStatus(Status.SUCESSO);
            } else {
                if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                    erroImovelNEncontrado = true;
                } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    erroImovelAlugado = true;
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
            String msg = (erroCliente && erroCorretor) ? "Cliente e corretor não encontrados" :
            erroCliente ? "Cliente não encontrado." :
            erroCorretor ? "Corretor não encontrado." :
            erroImovelNEncontrado ? "Imóvel não encontrado." :
            "Imóvel já alugado.";

            AluguelErroReturnDTO erroDTO = new AluguelErroReturnDTO();
            erroDTO.setAluguel(AluguelSuccesDTO.convert(aluguel));
            erroDTO.setMsgErro(msg);
            return erroDTO;
        }

        return AluguelSuccesDTO.convert(aluguel);
    }

    public void deleteAluguel(String identifier) {
        /* 
            Essa rota deve receber o identificador de um aluguel e deve deletar o aluguel. Ela deve
            chamar a rota que aluga/vende para mudar o status do imóvel para disponível.
        */
        Aluguel aluguel = aluguelRepository.findByIdentifier(identifier);
        if (aluguel == null) throw new AluguelDoesNotExistException();
        aluguelRepository.delete(aluguel);
    }

    public AluguelReturnDTO updateAluguel(String identifier, AluguelSaveDTO aluguelDTO) {
        /* 
            Essa rota deve receber o identificador de um aluguel e deve atualizar o aluguel. Ela deve
            chamar a rota que aluga/vende para mudar o status do imóvel para disponível.
        */
        Aluguel aluguel = aluguelRepository.findByIdentifier(identifier);
        if (aluguel == null) throw new AluguelDoesNotExistException();
        aluguel.setCpfCorretor(aluguelDTO.getCpfCorretor());
        aluguel.setCpfLocatario(aluguelDTO.getCpfLocatario());
        aluguel.setIdImovel(aluguelDTO.getIdImovel());
        aluguelRepository.save(aluguel);

        return AluguelSuccesDTO.convert(aluguel);
    }
    

}
