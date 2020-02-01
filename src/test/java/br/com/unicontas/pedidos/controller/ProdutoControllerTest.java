package br.com.unicontas.pedidos.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.unicontas.pedidos.entity.Produto;
import br.com.unicontas.pedidos.exception.ApiExceptionHandler;
import br.com.unicontas.pedidos.service.PedidoService;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoControllerTest {
	
	private static final String urlProduto = "/v1/produtos";
	
	@InjectMocks
	private ProdutoController controller;
	
	@Mock
	private PedidoService service;
	
	private MockMvc mock;
	
	
	@Before
	public void inicializar () {
		MockitoAnnotations.initMocks(this);
		mock = MockMvcBuilders
				.standaloneSetup(controller)
				.setControllerAdvice(new ApiExceptionHandler())
				.build();
	}
	
	@BeforeClass
    public static void configurar() {
        FixtureFactoryLoader.loadTemplates("br.com.unicontas.pedidos.fixture");
    }
	
	@Test
	public void deveRetornarTodosOsPedidosAoBuscar() throws Exception {
		final List<Produto> produtosFixture = Fixture.from(Produto.class).gimme(5, "valido");
		final String retorno = new ObjectMapper().writeValueAsString(produtosFixture);
		
		when(service.listarPedidos()).thenReturn(produtosFixture);	
		
		mock.perform(get(urlProduto)
				.contentType(MediaType.APPLICATION_JSON)
				)			
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(retorno));
	}
	
//	@Test
//	public void deveRetornarPedidoAoBuscarPorCodigo() throws Exception {
//		final List<Produto> produtosFixture = Fixture.from(Produto.class).gimme(5, "valido");
//		final String retorno = new ObjectMapper().writeValueAsString(produtosFixture);
//		
//		when(service.listarPedidos()).thenReturn(produtosFixture);	
//		
//		mock.perform(get(urlProduto)
//				.contentType(MediaType.APPLICATION_JSON)
//				)			
//				.andExpect(status().isOk())
//				.andExpect(MockMvcResultMatchers.content().string(retorno));
//	}

}
