package br.com.unicontas.pedidos.resource.handlers;

import java.util.ArrayList;
import java.util.List;

public class ErroPadraoValidacao extends ErroPadrao {

	private static final long serialVersionUID = 1L;

	List<MessagemDeErro> erros = new ArrayList<MessagemDeErro>();

	public ErroPadraoValidacao(Integer codigo, String msg) {
		super(codigo, msg);
	}

	public List<MessagemDeErro> getErros() {
		return erros;
	}

	public void addErros(String nome, String mensagem) {
		this.erros.add(new MessagemDeErro(nome, mensagem));
	}

}
