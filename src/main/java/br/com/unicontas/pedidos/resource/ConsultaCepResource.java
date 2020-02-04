package br.com.unicontas.pedidos.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unicontas.pedidos.business.ConsultaCepBusiness;
import br.com.unicontas.pedidos.dto.EnderecoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/cep")
@Api(value = "Api que consome api de consulta de cep")
@CrossOrigin(origins = "*")
public class ConsultaCepResource {

	@Autowired
	private ConsultaCepBusiness business;


	@GetMapping("/{cep}")
	@ApiOperation(value = "Busca um endereço atraves de um cep")
	public ResponseEntity<EnderecoDTO> consultaCep(@PathVariable String cep) {
		return ResponseEntity.ok().body(business.buscarEnderecoPorCep(cep));
	}


}
