package br.com.unicontas.pedidos.resource.handlers;

import java.io.Serializable;

public class ErroPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String msg;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ErroPadrao(Integer codigo, String msg) {
		this.codigo = codigo;
		this.msg = msg;
	}

}
