package br.com.unicontas.pedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.unicontas.pedidos.dto.EnderecoDTO;
import br.com.unicontas.pedidos.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsultaCepService {

	@Autowired
	private RestTemplate restTemplate;

	public EnderecoDTO consultarEnderecoPorCep(String cep) {

		log.info("Consultando serviço de terceiro...");

		String url = "https://viacep.com.br/ws/" + cep + "/json";

		try {

			ResponseEntity<EnderecoDTO> response = restTemplate.getForEntity(url, EnderecoDTO.class);

			EnderecoDTO end = response.getBody();
			
			if(end.getLocalidade() == null) {
				throw new BusinessException("Cep invalido!!");	
			}
			
			return end;

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new BusinessException("Erro ao consultar serviço de terceiro" + e.getMessage());
		}

	}

}
