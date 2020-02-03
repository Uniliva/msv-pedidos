package br.com.unicontas.pedidos.fixture.entity;

import java.time.LocalDate;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.unicontas.pedidos.entity.Pedido;
import br.com.unicontas.pedidos.entity.Produto;

public class PedidoFixture implements TemplateLoader {

	@Override
	public void load() {
		Fixture.of(Pedido.class).addTemplate("valido", new Rule() {
			{
				add("id", random(Long.class, range(1L, 500L)));
				add("qtdProdutos", 3D);
				add("dataPedido", LocalDate.now());
				add("listaProdutos", has(3).of(Produto.class, "valido"));
			}

		}).addTemplate("invalido", new Rule() {
			{
				add("id", random(Long.class, range(1L, 500L)));
				add("qtdProdutos", null);
				add("dataPedido", LocalDate.now());
				add("listaProdutos", has(3).of(Produto.class, "invalido"));
			}

		});
	}
}
