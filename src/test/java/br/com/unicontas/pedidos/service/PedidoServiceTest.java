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
import br.com.unicontas.pedidos.entity.Pedido;
import br.com.unicontas.pedidos.repository.PedidoRepository;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceTest {

	@InjectMocks
	private PedidoService service;

	@Mock
	private PedidoRepository repo;
	
	@BeforeClass
	public static void configurar() {
		FixtureFactoryLoader.loadTemplates("br.com.unicontas.pedidos.fixture");
	}
	
	 @Test
	 public void deveRetornarListaPedidos() {
		 final List<Pedido> pedidosFixture = Fixture.from(Pedido.class).gimme(5, "valido");
		 
		 when(repo.findAll()).thenReturn(pedidosFixture);
		 
		 List<Pedido> resultado = service.listar();
		 
		 assertEquals(pedidosFixture, resultado);
		 assertEquals(pedidosFixture.size(), resultado.size());
		 
		 verify(repo, timeout(1)).findAll();		 
	 }
	 
	 
	 @Test
	 public void deveRetornarPaginaComPedidos() {
		 final Integer pagina = 0;
		 final Integer itensPorPagina = 10;
		 final String ordenadoPor = "descricao";
		 final String direcaoOrdenacao  = "ASC";	 

		 final List<Pedido> pedidosFixture = Fixture.from(Pedido.class).gimme(5, "valido");
		 final Pageable pageRequest = PageRequest.of(pagina, itensPorPagina, Direction.valueOf(direcaoOrdenacao), ordenadoPor);		 
		 final Page<Pedido> paginaMock =  new PageImpl<Pedido>(pedidosFixture, pageRequest, pedidosFixture.size());
		 
		 when(repo.findAll(any(Pageable.class))).thenReturn(paginaMock);
       
		 Page<Pedido> resultado = service.listarPaginados(pagina, itensPorPagina, ordenadoPor, direcaoOrdenacao);

		
		 assertEquals(paginaMock, resultado);
		 assertEquals(paginaMock.getSize(), resultado.getSize());
		 
		 verify(repo, timeout(1)).findAll(any(Pageable.class));	 
	 }
	 
	 @Test
	 public void deveRetornarPedidosPorCodigo() {
		 final Pedido pedidoFixture = Fixture.from(Pedido.class).gimme("valido");
		 
		 when(repo.findById(any(Long.class))).thenReturn(Optional.of(pedidoFixture));
		 
		 Optional<Pedido> resultado = service.buscaPorCodigo(1L);
		 
		 assertTrue(resultado.isPresent());
		 assertEquals(pedidoFixture, resultado.get());
		 assertEquals(pedidoFixture.getId(), resultado.get().getId());
		 
		 verify(repo, timeout(1)).findById(any(Long.class));	 
	 }
	 
	 @Test
	 public void deveGravarPedidoAoSalvar() {
		 final Pedido pedidoFixture = Fixture.from(Pedido.class).gimme("valido");
		 
		 when(repo.save(any(Pedido.class))).thenReturn(pedidoFixture);
		 
		 service.salvar(pedidoFixture);
		 
		 verify(repo, timeout(1)).save(any(Pedido.class));	 
	 }
	 
	 @Test
	 public void deveApagarPedidoAoRemover() {
		 final Pedido pedidoFixture = Fixture.from(Pedido.class).gimme("valido");
		 
		 doNothing().when(repo).deleteById(any(Long.class));
		 
		 service.remover(pedidoFixture.getId());
		 
		 verify(repo, timeout(1)).deleteById(any(Long.class));
	 }
}
