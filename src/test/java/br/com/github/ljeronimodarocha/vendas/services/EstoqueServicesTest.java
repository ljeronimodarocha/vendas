package br.com.github.ljeronimodarocha.vendas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.github.ljeronimodarocha.vendas.MocksEstoque;
import br.com.github.ljeronimodarocha.vendas.domain.entity.Estoque;
import br.com.github.ljeronimodarocha.vendas.domain.entity.dto.request.EstoqueRequest;
import br.com.github.ljeronimodarocha.vendas.domain.repository.EstoqueRepository;

@ExtendWith(SpringExtension.class)
class EstoqueServicesTest extends MocksEstoque {

    @MockBean
    EstoqueRepository repository;

    EstoqueServices services;

    @MockBean
    ProdutoServices produtoServices;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setup() {
        services = new EstoqueServices(repository, produtoServices);

        when(repository.save(any()))
                .thenReturn(mockEstoque());
        when(produtoServices.buscaPeloId(anyLong())).thenReturn(mockProduto());
        when(repository.findById(anyLong())).thenReturn(Optional.of(mockEstoque()));
        when(repository.findAll(any(Example.class))).thenReturn(Collections.singletonList(mockEstoque()));
    }

    @SuppressWarnings("unchecked")
    @Test
    void deveListarEstoque() {
        List<Estoque> lista = services.listar(mockEstoque());

        verify(repository).findAll(any(Example.class));
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void deveBuscarPeloId() {
        Estoque estoque = services.buscaPeloId(1L);
        verify(repository).findById(anyLong());
        assertNotNull(estoque);
    }

    @Test
    void testDeveSalvarEstoque() {
        Estoque salvo = this.services.salvar(mockEstoqueRequest());

        assertNotNull(salvo);
        assertEquals(mockEstoque().getId(), salvo.getId());
        assertNotNull(salvo.getProduto());
        assertEquals(mockProduto().getId(), salvo.getProduto().getId());
        verify(repository).save(any());
    }

    @Test
    void deveAtualizarEstoque() {
        EstoqueRequest estoque = new EstoqueRequest(1L, BigDecimal.TWO, 10);
        services.atualizar(1L, estoque);
        verify(repository).save(any());
    }

    @Test
    void deveExcluir() {
        services.excluir(1L);
        verify(repository).deleteById(any());
    }

}
