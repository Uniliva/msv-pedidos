package br.com.unicontas.pedidos.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnderecoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;	
	private Boolean erro;
}
