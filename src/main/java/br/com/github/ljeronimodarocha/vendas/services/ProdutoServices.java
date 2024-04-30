package br.com.github.ljeronimodarocha.vendas.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.github.ljeronimodarocha.vendas.domain.entity.Produto;
import br.com.github.ljeronimodarocha.vendas.domain.entity.dto.request.ProdutoRequest;
import br.com.github.ljeronimodarocha.vendas.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProdutoServices {

    private ProdutoRepository repository;

    private ObjectMapper mapper;

    public Produto salvar(ProdutoRequest produto) {
        Produto produtoToSave = Produto.builder()
                .dataCriacao(LocalDateTime.now())
                .descricao(produto.getDescricao())
                .build();
        return repository.save(produtoToSave);
    }

    public Produto buscaPeloId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Produto> listar(ProdutoRequest produto) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(mapper.convertValue(produto, Produto.class), matcher);
        return repository.findAll(example);
    }

    public void atualizar(Long id, ProdutoRequest produto) {
        repository.findById(id).map(produtoEncontrado -> {
            produtoEncontrado.setDescricao(produto.getDescricao());
            repository.save(produtoEncontrado);
            return produtoEncontrado;
        }).orElseThrow();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

}
