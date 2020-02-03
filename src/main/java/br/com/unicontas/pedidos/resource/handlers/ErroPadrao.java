package br.com.unicontas.pedidos.resource.handlers;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String msg;

}
