package br.com.unicontas.pedidos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.unicontas.pedidos.entity.Pedido;
import br.com.unicontas.pedidos.entity.Produto;
import br.com.unicontas.pedidos.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	/**
	 * Lista todos os pedidos
	 * @return Todos os pedidos
	 */
	public List<Pedido> listar() {
		return repo.findAll();
	}
	

	/**
	 * Busca uma pagina de pedido deacordo com as dados passados abaixo:
	 * @param page - Numero da pagina a ser retonada
	 * @param itensPorPagina - quantidade de item que devem ser retornado
	 * @param ordenadoPor - item a ser usado para ordenar, [ex: id, descricao ..]
	 * @param direcaoOrdenacao - Direção da ordenação se ASC ou DESC
	 * @return Pagina com pedido
	 */
	public Page<Pedido> listarPaginados(Integer pagina, Integer itensPorPagina, String ordenadoPor, String direcaoOrdenacao) {
		PageRequest request = PageRequest.of(pagina, itensPorPagina, Direction.valueOf(direcaoOrdenacao), ordenadoPor);
		return repo.findAll(request);

	}
	
	/**
	 * Busca um pedido pelo codigo.
	 * @param codigo {@link Integer}
	 * @return Produto {@link Optional}
	 */
	public Optional<Pedido> buscaPorCodigo(Long codigo) {
		return repo.findById(codigo);
	}

	/**
	 * Salva um pedido no banco de dados.
	 * @param pedido {@link Produto}
	 * @return Pedido salvo {@link Pedido}
	 */
	public Pedido salvar(Pedido pedido) {		
		return repo.save(pedido);
	}

	/**
	 * Remove um pedido pelo ID
	 * @param id
	 */
	public void remover(Long id) {
		repo.deleteById(id);		
	}


}
