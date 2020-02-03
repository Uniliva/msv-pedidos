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

import br.com.unicontas.pedidos.business.PedidoBusiness;
import br.com.unicontas.pedidos.entity.Pedido;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/pedidos")
@Api(value = "Api de Pedidos")
@CrossOrigin(origins = "*")
public class PedidoResource {

	@Autowired
	private PedidoBusiness business;

	@GetMapping
	@ApiOperation(value = "Retorna uma lista de todos os pedidos salvos")
	public ResponseEntity<List<Pedido>> listar() {
		return ResponseEntity.ok(business.listar());
	}

	@GetMapping("/paginado")
	@ApiOperation(value = "Retorna os pedido de forma paginada")
	public ResponseEntity<Page<Pedido>> buscarPaginado(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "numeroItens", defaultValue = "24") Integer itensPorPagina,
			@RequestParam(value = "ordenadoPor", defaultValue = "nome") String ordenadoPor,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcaoOrdenacao) {
		return ResponseEntity
				.ok(business.listarPaginado(pagina, itensPorPagina, ordenadoPor, direcaoOrdenacao));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Busca um pedido através do codigo")
	public ResponseEntity<Pedido> buscarPorCodigo(@PathVariable Long id) {
		return ResponseEntity.ok().body(business.buscaPorCodigo(id));
	}

	@PostMapping
	@ApiOperation(value = "Salva um pedido")
	public ResponseEntity<Pedido> salvar(@Valid @RequestBody Pedido pedido) {
		Pedido obj = business.salvar(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Remove um pedido")
	public ResponseEntity<Void> apagar(@PathVariable Long id) {
		business.remover(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	@ApiOperation(value = "Atualiza um produto")
	public ResponseEntity<Pedido> atualizar(@Valid @RequestBody Pedido pedido) {
		return ResponseEntity.ok(business.atualizar(pedido));
	}

}
