package br.com.unicontas.pedidos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
	private Long id;
	private String descricao;
	private Double qtdEstoque;
	private String unidade;
	private Double valor;

}
