package br.com.unicontas.pedidos.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.unicontas.pedidos.entity.Produto;
import br.com.unicontas.pedidos.exception.BusinessException;
import br.com.unicontas.pedidos.exception.NotFoundException;
import br.com.unicontas.pedidos.service.ProdutoService;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoBusinessTest {

	@InjectMocks
	private ProdutoBusiness business;

	@Mock
	private ProdutoService service;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void configurar() {
		FixtureFactoryLoader.loadTemplates("br.com.unicontas.pedidos.fixture");
	}

	@Test
	public void deveRetornarListaProdutos() {
		final List<Produto> produtosFixture = Fixture.from(Produto.class).gimme(5, "valido");

		when(service.listarProdutos()).thenReturn(produtosFixture);

		List<Produto> resultado = business.listarProdutos();

		assertEquals(produtosFixture, resultado);
		assertEquals(produtosFixture.size(), resultado.size());

		verify(service, timeout(1)).listarProdutos();

	}

	@Test
	public void deveRetornarPaginaComProdutos() {
		final Integer pagina = 0;
		final Integer itensPorPagina = 10;
		final String ordenadoPor = "descricao";
		final String direcaoOrdenacao = "ASC";

		final List<Produto> produtosFixture = Fixture.from(Produto.class).gimme(5, "valido");
		final Pageable pageRequest = PageRequest.of(pagina, itensPorPagina, Direction.valueOf(direcaoOrdenacao),
				ordenadoPor);
		final Page<Produto> paginaMock = new PageImpl<Produto>(produtosFixture, pageRequest, produtosFixture.size());

		when(service.listarProdutosPaginados(any(Integer.class), any(Integer.class), any(String.class),
				any(String.class))).thenReturn(paginaMock);

		Page<Produto> resultado = business.listarProdutosPaginados(pagina, itensPorPagina, ordenadoPor,
				direcaoOrdenacao);

		assertEquals(paginaMock, resultado);
		assertEquals(paginaMock.getSize(), resultado.getSize());

		verify(service, timeout(1)).listarProdutosPaginados(any(Integer.class), any(Integer.class), any(String.class),
				any(String.class));
	}

	@Test
	public void deveRetornarProdutosPorCodigo() {
		final Produto produtoFixture = Fixture.from(Produto.class).gimme("valido");

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.of(produtoFixture));

		Produto resultado = business.buscaPorCodigo(1L);

		assertNotNull(resultado);
		assertEquals(produtoFixture, resultado);
		assertEquals(produtoFixture.getId(), resultado.getId());

		verify(service, timeout(1)).buscaPorCodigo(any(Long.class));
	}

	@Test
	public void deveLancarExceptionAoBuscarProdutosQueNaoExiste() {

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("Produto não encontrado");

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.ofNullable(null));

		business.buscaPorCodigo(1L);

		verify(service, timeout(1)).buscaPorCodigo(any(Long.class));
	}

	@Test
	public void deveGravarProdutoAoSalvar() {
		final Produto produtoFixture = Fixture.from(Produto.class).gimme("valido");

		when(service.salvarProduto(any(Produto.class))).thenReturn(produtoFixture);

		Produto resultado = business.salvarProduto(produtoFixture);

		assertEquals(produtoFixture, resultado);
		verify(service, timeout(1)).salvarProduto(any(Produto.class));
	}

	@Test
	public void deveApagarProdutoAoRemover() {
		final Produto produtoFixture = Fixture.from(Produto.class).gimme("valido");

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.of(produtoFixture));
		doNothing().when(service).removerProduto(any(Long.class));

		business.removerProduto(produtoFixture.getId());

		verify(service, timeout(1)).removerProduto(any(Long.class));
	}

	@Test
	public void deveLancarExceptionAoApagarProduto() {
		final Produto produtoFixture = Fixture.from(Produto.class).gimme("valido");

		expectedException.expect(BusinessException.class);
		expectedException.expectMessage("Não é possivel apagar um produto que está sendo utilizado");

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.of(produtoFixture));

		doThrow(DataIntegrityViolationException.class).when(service).removerProduto(any(Long.class));

		business.removerProduto(produtoFixture.getId());

		verify(service, timeout(1)).removerProduto(any(Long.class));
		verify(service, timeout(1)).buscaPorCodigo(any(Long.class));
	}
	
	@Test
	public void deveGravarProdutoAoAtualizar() {
		final Produto produtoFixture = Fixture.from(Produto.class).gimme("valido");
		final Produto produtoAtualizadoFixture = new Produto(produtoFixture.getId(), produtoFixture.getDescricao(), 200D, produtoFixture.getUnidade(), 5.55D);

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.of(produtoFixture));
		when(service.salvarProduto(any(Produto.class))).thenReturn(produtoAtualizadoFixture);

		Produto resultado = business.atualizar(produtoFixture);

		assertEquals(produtoAtualizadoFixture, resultado);
		assertNotEquals(produtoFixture.getQtdEstoque(), resultado.getQtdEstoque());
		
		verify(service, timeout(1)).salvarProduto(any(Produto.class));
		verify(service, timeout(1)).buscaPorCodigo(any(Long.class));
	}

}
