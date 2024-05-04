package br.com.github.ljeronimodarocha.vendas.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.github.ljeronimodarocha.vendas.domain.entity.Produto;
import br.com.github.ljeronimodarocha.vendas.domain.entity.dto.request.ProdutoRequest;
import br.com.github.ljeronimodarocha.vendas.services.ProdutoServices;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@AllArgsConstructor
public class ProdutoController {

    private ProdutoServices services;

    @GetMapping
    public ResponseEntity<List<Produto>> listar(ProdutoRequest produto) {
        return ResponseEntity.ok().body(services.listar(produto));
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody ProdutoRequest produto) {
        Produto produtoCriado = services.salvar(produto);
        return ResponseEntity.created(URI.create(produtoCriado.getId().toString())).body(produtoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ProdutoRequest produto) {
        services.atualizar(id, produto);
        return ResponseEntity.noContent().build();
    }

}
