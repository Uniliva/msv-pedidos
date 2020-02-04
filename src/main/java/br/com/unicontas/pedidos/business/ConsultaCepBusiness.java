package br.com.unicontas.pedidos.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.unicontas.pedidos.dto.EnderecoDTO;
import br.com.unicontas.pedidos.exception.BusinessException;
import br.com.unicontas.pedidos.service.ConsultaCepService;

@Component
public class ConsultaCepBusiness {
	

	@Autowired
	private ConsultaCepService service;
	
	public EnderecoDTO buscarEnderecoPorCep(String cep) {
		cep = cep.replace("-", "");
		if(cep.length() > 8) throw new BusinessException("O cep deve conter 8 digitos");
		return service.consultarEnderecoPorCep(cep);
	}
}
