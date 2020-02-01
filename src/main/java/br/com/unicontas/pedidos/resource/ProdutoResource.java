package br.com.unicontas.pedidos.resource;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/produtos")
@Api(value = "Api de Produtos")
@CrossOrigin(origins = "*")
public class ProdutoResource {

	@Autowired
	private ProdutoBusiness business;

	@GetMapping
	@ApiOperation(value = "Retorna uma lista de todos os produtos salvos")
	public ResponseEntity<List<Produto>> listar() {
		return ResponseEntity.ok(business.listarProdutos());
	}

	@GetMapping("/paginado")
	@ApiOperation(value = "Retorna os produtos de forma paginada")
	public ResponseEntity<Page<Produto>> buscarPaginado(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "numeroItens", defaultValue = "24") Integer itensPorPagina,
			@RequestParam(value = "ordenadoPor", defaultValue = "nome") String ordenadoPor,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcaoOrdenacao) {
		return ResponseEntity
				.ok(business.listarProdutosPaginados(pagina, itensPorPagina, ordenadoPor, direcaoOrdenacao));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Busca um produto através do codigo")
	public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Long id) {
		return ResponseEntity.ok().body(business.buscaPorCodigo(id));
	}

	@PostMapping
	@ApiOperation(value = "Salva um produto")
	public ResponseEntity<Produto> salvarProduto(@Valid @RequestBody Produto produto) {
		Produto obj = business.salvarProduto(produto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Remove um produto")
	public ResponseEntity<Void> apagarProduto(@PathVariable Long id) {
		business.removerProduto(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	@ApiOperation(value = "Atualiza um produto")
	public ResponseEntity<Produto> atualizarProduto(@Valid @RequestBody Produto produto) {
		return ResponseEntity.ok(business.atualizar(produto));
	}

}
