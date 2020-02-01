package br.com.unicontas.pedidos.service;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.unicontas.pedidos.entity.Produto;
import br.com.unicontas.pedidos.repository.ProdutoRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoServiceTest {

	@InjectMocks
	private ProdutoService service;

	@Mock
	private ProdutoRepository repo;
	
	@BeforeClass
	public static void configurar() {
		FixtureFactoryLoader.loadTemplates("br.com.unicontas.pedidos.fixture");
	}
	
	 @Test
	 public void deveRetornarListaPedidos() {
		 final List<Produto> produtosFixture = Fixture.from(Produto.class).gimme(5, "valido");
		 
		 when(repo.findAll()).thenReturn(produtosFixture);
		 
		 List<Produto> resultado = service.listarProdutos();
		 
		 assertEquals(produtosFixture, resultado);
		 assertEquals(produtosFixture.size(), resultado.size());
		 
		 verify(repo, timeout(1)).findAll();		 
	 }
	 
	 
	 @Test
	 public void deveRetornarPaginaComPedidos() {
		 final Integer pagina = 0;
		 final Integer itensPorPagina = 10;
		 final String ordenadoPor = "descricao";
		 final String direcaoOrdenacao  = "ASC";	 

		 final List<Produto> produtosFixture = Fixture.from(Produto.class).gimme(5, "valido");
		 final Pageable pageRequest = PageRequest.of(pagina, itensPorPagina, Direction.valueOf(direcaoOrdenacao), ordenadoPor);		 
		 final Page<Produto> paginaMock =  new PageImpl<Produto>(produtosFixture, pageRequest, produtosFixture.size());
		 
		 when(repo.findAll(any(Pageable.class))).thenReturn(paginaMock);
       
		 Page<Produto> resultado = service.listarProdutosPaginados(pagina, itensPorPagina, ordenadoPor, direcaoOrdenacao);

		
		 assertEquals(paginaMock, resultado);
		 assertEquals(paginaMock.getSize(), resultado.getSize());
		 
		 verify(repo, timeout(1)).findAll(any(Pageable.class));	 
	 }
	 
	 @Test
	 public void deveRetornarPedidosPorCodigo() {
		 final Produto produtoFixture = Fixture.from(Produto.class).gimme("valido");
		 
		 when(repo.findById(any(Long.class))).thenReturn(Optional.of(produtoFixture));
		 
		 Optional<Produto> resultado = service.buscaPorCodigo(1L);
		 
		 assertTrue(resultado.isPresent());
		 assertEquals(produtoFixture, resultado.get());
		 assertEquals(produtoFixture.getId(), resultado.get().getId());
		 
		 verify(repo, timeout(1)).findById(any(Long.class));	 
	 }
	 
	 @Test
	 public void deveGravarPedidoAoSalvar() {
		 final Produto produtoFixture = Fixture.from(Produto.class).gimme("valido");
		 
		 when(repo.save(any(Produto.class))).thenReturn(produtoFixture);
		 
		 service.salvarProduto(produtoFixture);
		 
		 verify(repo, timeout(1)).save(any(Produto.class));	 
	 }
	 
	 @Test
	 public void deveApagarPedidoAoRemover() {
		 final Produto produtoFixture = Fixture.from(Produto.class).gimme("valido");
		 
		 doNothing().when(repo).deleteById(any(Long.class));
		 
		 service.removerProduto(produtoFixture.getId());
		 
		 verify(repo, timeout(1)).deleteById(any(Long.class));
	 }
}
