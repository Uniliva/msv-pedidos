package br.com.unicontas.pedidos.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.unicontas.pedidos.business.ProdutoBusiness;
import br.com.unicontas.pedidos.entity.Produto;

@RestController
@RequestMapping("v1/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoBusiness business;

	@GetMapping
	public ResponseEntity<List<Produto>> listar() {
		return ResponseEntity.ok(business.listarProdutos());
	}

	@GetMapping("/paginado")
	public ResponseEntity<Page<Produto>> buscarPaginado(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "numeroItens", defaultValue = "24") Integer itensPorPagina,
			@RequestParam(value = "ordenadoPor", defaultValue = "nome") String ordenadoPor,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcaoOrdenacao) {
		return ResponseEntity
				.ok(business.listarProdutosPaginados(pagina, itensPorPagina, ordenadoPor, direcaoOrdenacao));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Long id) {
		return ResponseEntity.ok().body(business.buscaPorCodigo(id));
	}

	@PostMapping
	public ResponseEntity<Produto> salvarProduto(@Valid @RequestBody Produto produto) {
		Produto obj = business.salvarProduto(produto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> apagarProduto(@PathVariable Long id) {
		business.removerProduto(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	public ResponseEntity<Produto> atualizarProduto(@Valid @RequestBody Produto produto) {
		return ResponseEntity.ok(business.atualizar(produto));
	}

}
