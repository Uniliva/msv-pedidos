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
import br.com.unicontas.pedidos.entity.Pedido;
import br.com.unicontas.pedidos.entity.Produto;
import br.com.unicontas.pedidos.exception.BusinessException;
import br.com.unicontas.pedidos.exception.NotFoundException;
import br.com.unicontas.pedidos.service.PedidoService;

@RunWith(MockitoJUnitRunner.class)
public class PedidoBusinessTest {

	@InjectMocks
	private PedidoBusiness business;

	@Mock
	private PedidoService service;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void configurar() {
		FixtureFactoryLoader.loadTemplates("br.com.unicontas.pedidos.fixture");
	}

	@Test
	public void deveRetornarListaPedidos() {
		final List<Pedido> pedidosFixture = Fixture.from(Pedido.class).gimme(5, "valido");

		when(service.listar()).thenReturn(pedidosFixture);

		List<Pedido> resultado = business.listar();

		assertEquals(pedidosFixture, resultado);
		assertEquals(pedidosFixture.size(), resultado.size());

		verify(service, timeout(1)).listar();

	}

	@Test
	public void deveRetornarPaginaComPedidos() {
		final Integer pagina = 0;
		final Integer itensPorPagina = 10;
		final String ordenadoPor = "descricao";
		final String direcaoOrdenacao = "ASC";

		final List<Pedido> pedidosFixture = Fixture.from(Pedido.class).gimme(5, "valido");
		final Pageable pageRequest = PageRequest.of(pagina, itensPorPagina, Direction.valueOf(direcaoOrdenacao),
				ordenadoPor);
		final Page<Pedido> paginaMock = new PageImpl<Pedido>(pedidosFixture, pageRequest, pedidosFixture.size());

		when(service.listarPaginados(any(Integer.class), any(Integer.class), any(String.class),
				any(String.class))).thenReturn(paginaMock);

		Page<Pedido> resultado = business.listarPaginado(pagina, itensPorPagina, ordenadoPor,
				direcaoOrdenacao);

		assertEquals(paginaMock, resultado);
		assertEquals(paginaMock.getSize(), resultado.getSize());

		verify(service, timeout(1)).listarPaginados(any(Integer.class), any(Integer.class), any(String.class),
				any(String.class));
	}

	@Test
	public void deveRetornarPedidosPorCodigo() {
		final Pedido pedidoFixture = Fixture.from(Pedido.class).gimme("valido");

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.of(pedidoFixture));

		Pedido resultado = business.buscaPorCodigo(1L);

		assertNotNull(resultado);
		assertEquals(pedidoFixture, resultado);
		assertEquals(pedidoFixture.getId(), resultado.getId());

		verify(service, timeout(1)).buscaPorCodigo(any(Long.class));
	}

	@Test
	public void deveLancarExceptionAoBuscarPedidosQueNaoExiste() {

		expectedException.expect(NotFoundException.class);
		expectedException.expectMessage("Pedido não encontrado");

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.ofNullable(null));

		business.buscaPorCodigo(1L);

		verify(service, timeout(1)).buscaPorCodigo(any(Long.class));
	}

	@Test
	public void deveGravarPedidoAoSalvar() {
		final Pedido pedidoFixture = Fixture.from(Pedido.class).gimme("valido");

		when(service.salvar(any(Pedido.class))).thenReturn(pedidoFixture);

		Pedido resultado = business.salvar(pedidoFixture);

		assertEquals(pedidoFixture, resultado);
		verify(service, timeout(1)).salvar(any(Pedido.class));
	}

	@Test
	public void deveApagarPedidoAoRemover() {
		final Pedido pedidoFixture = Fixture.from(Pedido.class).gimme("valido");

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.of(pedidoFixture));
		doNothing().when(service).remover(any(Long.class));

		business.remover(pedidoFixture.getId());

		verify(service, timeout(1)).remover(any(Long.class));
	}

	@Test
	public void deveLancarExceptionAoApagarPedido() {
		final Pedido pedidoFixture = Fixture.from(Pedido.class).gimme("valido");

		expectedException.expect(BusinessException.class);
		expectedException.expectMessage("Não é possivel apagar um pedido que está sendo utilizado");

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.of(pedidoFixture));

		doThrow(DataIntegrityViolationException.class).when(service).remover(any(Long.class));

		business.remover(pedidoFixture.getId());

		verify(service, timeout(1)).remover(any(Long.class));
		verify(service, timeout(1)).buscaPorCodigo(any(Long.class));
	}
	
	@Test
	public void deveGravarPedidoAoAtualizar() {
		final Pedido pedidoFixture = Fixture.from(Pedido.class).gimme("valido");
		final List<Produto> lstProdutos = Fixture.from(Produto.class).gimme(5, "valido");
		final Pedido produtoAtualizadoFixture = new Pedido(pedidoFixture.getId(), 0D,pedidoFixture.getDataPedido(), lstProdutos);

		when(service.buscaPorCodigo(any(Long.class))).thenReturn(Optional.of(pedidoFixture));
		when(service.salvar(any(Pedido.class))).thenReturn(produtoAtualizadoFixture);

		Pedido resultado = business.atualizar(pedidoFixture);

		assertEquals(produtoAtualizadoFixture, resultado);
		assertNotEquals(pedidoFixture.getQtdProdutos(), resultado.getQtdProdutos());
		
		verify(service, timeout(1)).salvar(any(Pedido.class));
		verify(service, timeout(1)).buscaPorCodigo(any(Long.class));
	}

}
