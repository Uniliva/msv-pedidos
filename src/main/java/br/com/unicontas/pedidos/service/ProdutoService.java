package br.com.unicontas.pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.unicontas.pedidos.entity.Produto;
import br.com.unicontas.pedidos.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	/**
	 * Busca um apagina de produtos deacordo com as dados passados abaixo:
	 * @param page - Numero da pagina a ser retonada
	 * @param itensPorPagina - quantidade de item que devem ser retornado
	 * @param ordenadoPor - item a ser usado para ordenar, [ex: id, descricao ..]
	 * @param direcaoOrdenacao - Direção da ordenação se ASC ou DESC
	 * @return Pagina com produtos
	 */
	public Page<Produto> listarProdutosPaginados(Integer pagina, Integer itensPorPagina, String ordenadoPor, String direcaoOrdenacao) {
		PageRequest request = PageRequest.of(pagina, itensPorPagina, Direction.valueOf(direcaoOrdenacao), ordenadoPor);
		return repo.findAll(request);

	}

	/**
	 * Lista todos os produtos
	 * @return Todos os produtos
	 */
	public List<Produto> listarProdutos() {
		return repo.findAll();
	}
	
	/**
	 * Busca um produto pelo codigo.
	 * @param codigo {@link Integer}
	 * @return Produto {@link Optional}
	 */
	public Optional<Produto> buscaPorCodigo(Long codigo) {
		return repo.findById(codigo);
	}

	/**
	 * Salva um produto no banco de dados.
	 * @param produto {@link Produto}
	 * @return produto salvo {@link Produto}
	 */
	public Produto salvarProduto(Produto produto) {		
		return repo.save(produto);
	}

	/**
	 * Remove um produto pelo ID
	 * @param id
	 */
	public void removerProduto(Long id) {
		repo.deleteById(id);		
	}

}
