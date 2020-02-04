package br.com.unicontas.pedidos.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.unicontas.pedidos.dto.EnderecoDTO;
import br.com.unicontas.pedidos.service.ConsultaCepService;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaCepBusinessTest {

	@InjectMocks
	private ConsultaCepBusiness business;

	@Mock
	private ConsultaCepService service;

	@BeforeClass
	public static void configurar() {
		FixtureFactoryLoader.loadTemplates("br.com.unicontas.pedidos.fixture");
	}

	@Test
	public void deveConsultaEnderecoPorCep() {

		final EnderecoDTO fixture = Fixture.from(EnderecoDTO.class).gimme("valido");

		when(service.consultarEnderecoPorCep(anyString())).thenReturn(fixture);

		EnderecoDTO endereco = business.buscarEnderecoPorCep("06250310");

		assertNotNull(endereco);
		assertEquals(fixture, endereco);

	}
}
