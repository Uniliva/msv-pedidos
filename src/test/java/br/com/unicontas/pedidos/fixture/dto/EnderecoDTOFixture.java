package br.com.unicontas.pedidos.fixture.dto;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.unicontas.pedidos.dto.EnderecoDTO;

public class EnderecoDTOFixture implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(EnderecoDTO.class).addTemplate("valido", new Rule() {
			{
				add("cep", random("06608000", "17720000"));
				add("logradouro", random("Avenida Paulista", "Rua Ibirapuera"));
				add("complemento", "Casa 10");
				add("bairro", "Helena Maria");
				add("localidade", "Osasco");
				add("uf", "SP");
				add("erro", false);
			}

		}).addTemplate("invalido", new Rule() {
			{
				add("erro", true);
			}

		});
	}
}

