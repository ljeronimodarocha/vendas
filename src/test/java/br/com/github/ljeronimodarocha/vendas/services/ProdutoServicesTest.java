package br.com.github.ljeronimodarocha.vendas.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.github.ljeronimodarocha.vendas.MocksProduto;
import br.com.github.ljeronimodarocha.vendas.domain.entity.Produto;
import br.com.github.ljeronimodarocha.vendas.domain.repository.ProdutoRepository;

@ExtendWith(SpringExtension.class)
class ProdutoServicesTest extends MocksProduto {

    @MockBean
    ProdutoRepository repository;

    ProdutoServices services;

    ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setup() {
        services = new ProdutoServices(repository, mapper);

        when(repository.save(any()))
                .thenReturn(mockProduto());
        when(repository.findById(any())).thenReturn(Optional.of(mockProduto()));
        when(repository.findAll(any(Example.class))).thenReturn(Collections.singletonList(mockProduto()));
    }

    @SuppressWarnings("unchecked")
    @Test
    void deveListarEstoque() {
        List<Produto> lista = services.listar(mockProdutoRequest());

        verify(repository).findAll(any(Example.class));
        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    void deveBuscarPeloId() {
        Produto produto = services.buscaPeloId(1L);
        verify(repository).findById(anyLong());
        assertNotNull(produto);
    }

    @Test
    void testDeveSalvarEstoque() {
        Produto salvo = this.services.salvar(mockProdutoRequest());

        assertNotNull(salvo);
        assertEquals(mockProduto().getId(), salvo.getId());
        assertEquals(mockProduto().getDescricao(), salvo.getDescricao());
        verify(repository).save(any());
    }

    @Test
    void deveAtualizarEstoque() {
        services.atualizar(1L, mockProdutoRequest());
        verify(repository).save(any());
    }

    @Test
    void deveExcluir() {
        services.excluir(1L);
        verify(repository).deleteById(any());
    }

}
