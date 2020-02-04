package br.com.unicontas.pedidos.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.unicontas.pedidos.dto.EnderecoDTO;
import br.com.unicontas.pedidos.service.ConsultaCepService;

@Component
public class ConsultaCepBusiness {
	

	@Autowired
	private ConsultaCepService service;
	
	public EnderecoDTO buscarEnderecoPorCep(String cep) {
		return service.consultarEnderecoPorCep(cep);
	}
}
