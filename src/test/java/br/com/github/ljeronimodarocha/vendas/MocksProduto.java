package br.com.github.ljeronimodarocha.vendas;

import java.time.LocalDateTime;

import br.com.github.ljeronimodarocha.vendas.domain.entity.Produto;
import br.com.github.ljeronimodarocha.vendas.domain.entity.dto.request.ProdutoRequest;

public abstract class MocksProduto {

    protected Produto mockProduto() {
        return new Produto(1L, "teste", LocalDateTime.now());
    }

    protected ProdutoRequest mockProdutoRequest() {
        return new ProdutoRequest("Teste");
    }

}
