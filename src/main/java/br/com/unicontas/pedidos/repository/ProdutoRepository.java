package br.com.unicontas.pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unicontas.pedidos.entity.Produto;

@Repository
public interface ProdutoRepository  extends JpaRepository<Produto, Long>{

}
