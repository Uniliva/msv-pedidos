package br.com.unicontas.pedidos.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.unicontas.pedidos.entity.Produto;
import br.com.unicontas.pedidos.exception.BusinessException;
import br.com.unicontas.pedidos.exception.NotFoundException;
import br.com.unicontas.pedidos.service.ProdutoService;

@Component
public class ProdutoBusiness {

	@Autowired
	private ProdutoService service;

	public List<Produto> listarProdutos() {
		return service.listarProdutos();
	}

	public Page<Produto> listarProdutosPaginados(Integer pagina, Integer itensPorPagina, String ordenadoPor,
			String direcaoOrdenacao) {
		return service.listarProdutosPaginados(pagina, itensPorPagina, ordenadoPor, direcaoOrdenacao);
	}

	public Produto buscaPorCodigo(Long codigo) {
		return service.buscaPorCodigo(codigo).orElseThrow(() -> new NotFoundException("Produto não encontrado"));
	}

	public Produto salvarProduto(Produto produto) {
		return service.salvarProduto(produto);		
	}

	public void removerProduto(Long id) {
		Produto produto = buscaPorCodigo(id);
		try {
			service.removerProduto(produto.getId());
		} catch (DataIntegrityViolationException e) {
			throw new BusinessException("Não é possivel apagar um produto que está sendo utilizado");
		}
				
	}

	public Produto atualizar(Produto produto) {
		buscaPorCodigo(produto.getId());
		return service.salvarProduto(produto);
	}

}
