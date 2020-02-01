package br.com.unicontas.pedidos.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_PRODUTOS")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "COD_PRODUTO")
	private Long id;
	
	@Column(name = "DESCRICAO")
	@NotNull(message="Campo Obrigatorio!")
	@Size(min=5, max=80, message="O nome deve conter de 5 a 80 caracter")
	private String descricao;
	
	@Column(name = "QUANTIDADE_ESTOQUE")
	@NotNull(message="Campo Obrigatorio!")
	private Double qtdEstoque;
	
	@Column(name = "UNIDADE_MEDIDA")
	@NotNull(message="Campo Obrigatorio!")
	private String unidade;
	
	@Column(name = "PRECO")
	@NotNull(message="Campo Obrigatorio!")
	private Double valor;

}
