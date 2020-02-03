package br.com.unicontas.pedidos.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.unicontas.pedidos.entity.Pedido;
import br.com.unicontas.pedidos.exception.BusinessException;
import br.com.unicontas.pedidos.exception.NotFoundException;
import br.com.unicontas.pedidos.service.PedidoService;

@Component
public class PedidoBusiness {

	@Autowired
	private PedidoService service;
	
	@Autowired
	private ProdutoBusiness produtoService;

	public List<Pedido> listar() {
		return service.listar();
	}

	public Page<Pedido> listarPaginado(Integer pagina, Integer itensPorPagina, String ordenadoPor,
			String direcaoOrdenacao) {
		return service.listarPaginados(pagina, itensPorPagina, ordenadoPor, direcaoOrdenacao);
	}

	public Pedido buscaPorCodigo(Long codigo) {
		return service.buscaPorCodigo(codigo).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
	}

	public Pedido salvar(Pedido pedido) {
		pedido.getListaProdutos().stream().forEach(produto -> produtoService.buscaPorCodigo(pedido.getId()));
		return service.salvar(pedido);		
	}

	public void remover(Long id) {
		Pedido pedido = buscaPorCodigo(id);
		try {
			service.remover(pedido.getId());
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("Não é possivel apagar um pedido que está sendo utilizado");
		}
				
	}

	public Pedido atualizar(Pedido pedido) {
		buscaPorCodigo(pedido.getId());
		return service.salvar(pedido);
	}

}
