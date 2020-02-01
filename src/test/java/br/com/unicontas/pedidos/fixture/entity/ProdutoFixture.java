package br.com.unicontas.pedidos.fixture.entity;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.unicontas.pedidos.entity.Produto;

public class ProdutoFixture implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Produto.class).addTemplate("valido", new Rule() {
			{
				add("id", random(Long.class, range(1L, 500L)));
				add("descricao", name());
				add("qtdEstoque", random(Double.class, range(1L, 100L)));
				add("unidade", random("Caixa", "Barra", "Metro", "Litro"));
				add("valor", random(Double.class, range(1L, 500L)));
			}

		});
	}
}
