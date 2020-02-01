package br.com.unicontas.pedidos.resource.handlers;

import java.io.Serializable;

public class MessagemDeErro implements Serializable {

	private static final long serialVersionUID = 1L;
	private String campo;
	private String mensagem;

	public MessagemDeErro() {
	}

	/**
	 * @param nome
	 * @param mensagem
	 */
	public MessagemDeErro(String nome, String mensagem) {
		super();
		this.campo = nome;
		this.mensagem = mensagem;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
