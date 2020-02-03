package br.com.unicontas.pedidos.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TB_PEDIDO")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "COD_PEDIDO")
	@JsonProperty("numero_pedido")
	private Long id;
	
	@Column(name = "QUANTIDADE_PRODUTOS")
	@NotNull(message="Campo Obrigatorio!")
	@JsonProperty("quantidade_produtos")
	private Double qtdProdutos;
	
	@Column(name = "DATA_PEDIDO", columnDefinition = "DATE")
	@NotNull(message="Campo Obrigatorio!")
	@JsonProperty("data_pedido")
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataPedido;
	
	@ManyToMany
	@JoinTable(name="TB_ITENS_PEDIDO",  
                joinColumns=@JoinColumn(name="COD_PEDIDO"),   
                inverseJoinColumns=@JoinColumn(name="COD_PRODUTO")) 
	@JsonProperty("produtos")
	private Collection<Produto> listaProdutos = new ArrayList<>();

	
	public Double getQtdProdutos() {
		return (double) listaProdutos.size();
	}
	
	public void setQtdProdutos(Double qtdProdutos) {
		this.qtdProdutos = (double) listaProdutos.size();
	}
	
	
	
	
	
	
	

}
