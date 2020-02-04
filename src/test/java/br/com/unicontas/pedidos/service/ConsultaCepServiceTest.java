package br.com.unicontas.pedidos.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.unicontas.pedidos.dto.EnderecoDTO;
import br.com.unicontas.pedidos.exception.BusinessException;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaCepServiceTest {
	
	@InjectMocks
	private ConsultaCepService service;

	@Mock
	private RestTemplate restTemplate;
	

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void configurar() {
		FixtureFactoryLoader.loadTemplates("br.com.unicontas.pedidos.fixture");
	}

	@Test
	public void deveConsultaEnderecoPorCep() {

		final EnderecoDTO fixture = Fixture.from(EnderecoDTO.class).gimme("valido");

		ResponseEntity<EnderecoDTO> response = new ResponseEntity<EnderecoDTO>(fixture, HttpStatus.OK);

		when(restTemplate.getForEntity(anyString(), eq(EnderecoDTO.class))).thenReturn(response);

		EnderecoDTO endereco = service.consultarEnderecoPorCep("06251020");
		
		assertNotNull(endereco);
		assertEquals(fixture, endereco);

	}
	
	@Test
	public void devLancarExceptionAoConsultaEnderecoComCepInvalido() {

		final EnderecoDTO fixture = Fixture.from(EnderecoDTO.class).gimme("invalido");
		
		expectedException.expect(BusinessException.class);
		expectedException.expectMessage("Cep invalido!!");

		ResponseEntity<EnderecoDTO> response = new ResponseEntity<EnderecoDTO>(fixture, HttpStatus.OK);

		when(restTemplate.getForEntity(anyString(), eq(EnderecoDTO.class))).thenReturn(response);

		service.consultarEnderecoPorCep("06251020");

	}
	
	@Test
	public void devLancarHttpServerErrorExceptionAoConsultaEnderecoEDerErroNoServicoExterno() {
		
		expectedException.expect(BusinessException.class);

		when(restTemplate.getForEntity(anyString(), eq(EnderecoDTO.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro Interno"));

		service.consultarEnderecoPorCep("06251020");

	}
	
	@Test
	public void devLancarHttpClientErrorExceptionAoConsultaEnderecoEDerNORequest() {
		
		expectedException.expect(BusinessException.class);

		when(restTemplate.getForEntity(anyString(), eq(EnderecoDTO.class))).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Requisicão Invalida"));

		service.consultarEnderecoPorCep("06251020");

	}

}
