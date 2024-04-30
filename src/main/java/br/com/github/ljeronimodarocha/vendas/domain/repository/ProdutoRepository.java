package br.com.github.ljeronimodarocha.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.github.ljeronimodarocha.vendas.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
