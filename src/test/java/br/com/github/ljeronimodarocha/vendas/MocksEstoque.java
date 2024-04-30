package br.com.github.ljeronimodarocha.vendas;

import java.math.BigDecimal;

import br.com.github.ljeronimodarocha.vendas.domain.entity.Estoque;
import br.com.github.ljeronimodarocha.vendas.domain.entity.dto.request.EstoqueRequest;

public abstract class MocksEstoque extends MocksProduto {

    protected EstoqueRequest mockEstoqueRequest() {
        Estoque estoque = mockEstoque();
        return new EstoqueRequest(mockProduto().getId(), estoque.getValor(), estoque.getQuantidade());
    }

    protected Estoque mockEstoque() {
        return new Estoque(1L, BigDecimal.TEN, mockProduto(), 10);
    }

}
