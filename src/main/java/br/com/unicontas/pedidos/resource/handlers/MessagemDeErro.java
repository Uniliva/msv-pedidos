package br.com.unicontas.pedidos.resource.handlers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessagemDeErro implements Serializable {

	private static final long serialVersionUID = 1L;
	private String campo;
	private String mensagem;

}
