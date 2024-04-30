package br.com.github.ljeronimodarocha.vendas.services;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import br.com.github.ljeronimodarocha.vendas.domain.entity.Estoque;
import br.com.github.ljeronimodarocha.vendas.domain.entity.Produto;
import br.com.github.ljeronimodarocha.vendas.domain.entity.dto.request.EstoqueRequest;
import br.com.github.ljeronimodarocha.vendas.domain.repository.EstoqueRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstoqueServices {

    private EstoqueRepository repository;

    private ProdutoServices produtoServices;

    public Estoque salvar(EstoqueRequest estoque) {
        Produto produto = produtoServices.buscaPeloId(estoque.produtoId());
        Estoque estoqueToSave = converteParaEstoque(estoque);
        estoqueToSave.setProduto(produto);
        return repository.save(estoqueToSave);
    }

    public List<Estoque> listar(Estoque produto) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Estoque> example = Example.of(produto, matcher);
        return repository.findAll(example);
    }

    public Estoque buscaPeloId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void atualizar(Long id, EstoqueRequest estoque) {
        repository.findById(id).map(estoqueEncontrado -> {
            if (estoque.quantidade() != null)
                estoqueEncontrado.setQuantidade(estoque.quantidade());
            if (estoque.valor() != null)
                estoqueEncontrado.setValor(estoque.valor());
            repository.save(estoqueEncontrado);
            return estoqueEncontrado;
        }).orElseThrow();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private static Estoque converteParaEstoque(EstoqueRequest request) {
        return Estoque.builder()
                .valor(request.valor())
                .quantidade(request.quantidade())
                .produto(Produto.builder()
                        .id(request.produtoId())
                        .build())
                .build();
    }

}
