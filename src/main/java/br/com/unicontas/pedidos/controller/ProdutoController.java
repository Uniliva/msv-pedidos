package br.com.unicontas.pedidos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unicontas.pedidos.entity.Produto;
import br.com.unicontas.pedidos.service.PedidoService;

@RestController
@RequestMapping("v1/produtos")
public class ProdutoController {
	
	@Autowired
	private PedidoService service;
	
	@GetMapping
	public ResponseEntity<List<Produto>> listar() {
		return ResponseEntity.ok(service.listarPedidos());
	}

}
