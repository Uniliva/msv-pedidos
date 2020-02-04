package br.com.unicontas.pedidos.resource;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import br.com.unicontas.pedidos.business.ConsultaCepBusiness;
import br.com.unicontas.pedidos.dto.EnderecoDTO;
import br.com.unicontas.pedidos.exception.BusinessException;
import br.com.unicontas.pedidos.resource.handlers.ErroPadrao;
import br.com.unicontas.pedidos.resource.handlers.ResourceExceptionHandler;

@RunWith(MockitoJUnitRunner.class)
public class ConsutaCepResourceTest {

	private static final String urlBase = "/v1/cep";

	@InjectMocks
	private ConsultaCepResource resource;

	@Mock
	private ConsultaCepBusiness business;

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
	public void deveRetornarEnderecoAoConsultaCep() throws Exception {
		final EnderecoDTO fixture = Fixture.from(EnderecoDTO.class).gimme("valido");
		final String retorno = new ObjectMapper().writeValueAsString(fixture);

		when(business.buscarEnderecoPorCep(anyString())).thenReturn(fixture);

		mock.perform(get(urlBase + "/06250310").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print()).andExpect(MockMvcResultMatchers.content().string(retorno));
	}

	@Test
	public void deveRetornar400AoBuscarProdutoInexiste() throws Exception {
		final ErroPadrao erroPadrao = new ErroPadrao(400, "Cep invalido!!");
		final String retorno = new ObjectMapper().writeValueAsString(erroPadrao);

		when(business.buscarEnderecoPorCep(anyString())).thenThrow(new BusinessException("Cep invalido!!"));

		mock.perform(get(urlBase + "/10").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError()).andDo(print())
				.andExpect(MockMvcResultMatchers.content().string(retorno));
	}
}
