package br.com.github.ljeronimodarocha.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.github.ljeronimodarocha.vendas.domain.entity.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

}
