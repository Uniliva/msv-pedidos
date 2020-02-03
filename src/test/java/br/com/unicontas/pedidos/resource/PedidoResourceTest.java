package br.com.unicontas.pedidos.resource;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.unicontas.pedidos.business.PedidoBusiness;
import br.com.unicontas.pedidos.entity.Pedido;
import br.com.unicontas.pedidos.exception.BusinessException;
import br.com.unicontas.pedidos.exception.NotFoundException;
import br.com.unicontas.pedidos.resource.handlers.ErroPadrao;
import br.com.unicontas.pedidos.resource.handlers.ResourceExceptionHandler;

@RunWith(MockitoJUnitRunner.class)
public class PedidoResourceTest {

	private static final String urlBase = "/v1/pedidos";

	@InjectMocks
	private PedidoResource resource;

	@Mock
	private PedidoBusiness business;

	private MockMvc mock;

	@Before
	public void inicializar() {
		MockitoAnnotations.initMocks(this);
		mock = MockMvcBuilders.standaloneSetup(resource).setControllerAdvice(new ResourceExceptionHandler()).build();
	}

	@BeforeClass
	public static void configurar() {
		FixtureFactoryLoader.loadTemplates("br.com.unicontas.pedidos.fixture");
	}

	@Test
	public void deveRetornarTodosOsPedidosAoBuscar() throws Exception {
		final List<Pedido> fixture = Fixture.from(Pedido.class).gimme(5, "valido");
		final String retorno = new ObjectMapper().writeValueAsString(fixture);

		when(business.listar()).thenReturn(fixture);

		mock.perform(get(urlBase).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(print())
				.andExpect(MockMvcResultMatchers.content().string(retorno));
	}

	@Test
	public void deveRetornarPaginaComPedidosAoBuscarPaginado() throws Exception {

		final Integer pagina = 0;
		final Integer itensPorPagina = 10;
		final String ordenadoPor = "descricao";
		final String direcaoOrdenacao = "ASC";

		final List<Pedido> fixture = Fixture.from(Pedido.class).gimme(5, "valido");
		final Pageable pageRequest = PageRequest.of(pagina, itensPorPagina, Direction.valueOf(direcaoOrdenacao),
				ordenadoPor);
		final Page<Pedido> paginaMock = new PageImpl<Pedido>(fixture, pageRequest, fixture.size());

		when(business.listarPaginado(any(Integer.class), any(Integer.class), any(String.class),
				any(String.class))).thenReturn(paginaMock);

		final String retorno = new ObjectMapper().writeValueAsString(paginaMock);

		mock.perform(get(urlBase + "/paginado").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andExpect(MockMvcResultMatchers.content().string(retorno));
	}

	@Test
	public void deveRetornarPedidoAoBuscarPorCodigo() throws Exception {
		final Pedido fixture = Fixture.from(Pedido.class).gimme("valido");
		final String retorno = new ObjectMapper().writeValueAsString(fixture);

		when(business.buscaPorCodigo(any(Long.class))).thenReturn(fixture);

		mock.perform(get(urlBase + "/10").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andExpect(MockMvcResultMatchers.content().string(retorno));
	}

	@Test
	public void deveRetornar400AoBuscarPedidoInexiste() throws Exception {
		final ErroPadrao erroPadrao = new ErroPadrao(400, "Pedido nao encontrado");
		final String retorno = new ObjectMapper().writeValueAsString(erroPadrao);

		when(business.buscaPorCodigo(any(Long.class))).thenThrow(new NotFoundException("Pedido nao encontrado"));

		mock.perform(get(urlBase + "/10").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andDo(print())
				.andExpect(MockMvcResultMatchers.content().string(retorno));
	}

	@Test
	public void deveGravarPedidoComSucesso() throws Exception {
		final Pedido fixture = Fixture.from(Pedido.class).gimme("valido");
		final String body = new ObjectMapper().writeValueAsString(fixture);

		when(business.salvar(any(Pedido.class))).thenReturn(fixture);

		mock.perform(post(urlBase).content(body).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(print())
				.andExpect(header().string("Location", containsString(urlBase + "/" + fixture.getId())));
	}

	@Test
	public void deveRetornar400AoEnviarPedidoInvalidoNaRequesicao() throws Exception {
		final Pedido fixture = Fixture.from(Pedido.class).gimme("invalido");
		final String body = new ObjectMapper().writeValueAsString(fixture);

		mock.perform(post(urlBase).content(body).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andDo(print())
				.andExpect(MockMvcResultMatchers.content().string(containsString("Erro de valida")));
	}

	@Test
	public void deveApagarPedidoComSucesso() throws Exception {
		final Long codigo = 10L;

		doNothing().when(business).remover(any(Long.class));

		mock.perform(delete(urlBase + "/" + codigo).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	public void deveRetornar400AoApagarPedidoInexiste() throws Exception {
		final ErroPadrao erroPadrao = new ErroPadrao(400, "Pedido nao encontrado");
		final Long codigo = 120L;
		final String retorno = new ObjectMapper().writeValueAsString(erroPadrao);

		doThrow(new NotFoundException("Pedido nao encontrado")).when(business).remover(any(Long.class));

		mock.perform(delete(urlBase + "/" + codigo).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andDo(print())
				.andExpect(MockMvcResultMatchers.content().string(retorno));

	}

	@Test
	public void deveRetornar400AoApagarPedidoQueEstaSendoUsado() throws Exception {
		final ErroPadrao erroPadrao = new ErroPadrao(400, "Nao e possivel apagar um pedido que esta sendo utilizado");
		final Long codigo = 1L;
		final String retorno = new ObjectMapper().writeValueAsString(erroPadrao);

		doThrow(new BusinessException("Nao e possivel apagar um pedido que esta sendo utilizado")).when(business)
				.remover(any(Long.class));

		mock.perform(delete(urlBase + "/" + codigo).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andDo(print())
				.andExpect(MockMvcResultMatchers.content().string(retorno));

	}

	@Test
	public void deveGravarPedidoComSucessoAoAtualizar() throws Exception {

		final Pedido fixture = Fixture.from(Pedido.class).gimme("valido");
		final String body = new ObjectMapper().writeValueAsString(fixture);

		when(business.atualizar(any(Pedido.class))).thenReturn(fixture);

		mock.perform(put(urlBase).content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andExpect(MockMvcResultMatchers.content().string(body));
	}

}
