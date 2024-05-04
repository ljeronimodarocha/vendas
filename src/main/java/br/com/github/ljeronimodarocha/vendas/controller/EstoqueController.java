package br.com.github.ljeronimodarocha.vendas.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.github.ljeronimodarocha.vendas.domain.entity.Estoque;
import br.com.github.ljeronimodarocha.vendas.domain.entity.dto.request.EstoqueRequest;
import br.com.github.ljeronimodarocha.vendas.services.EstoqueServices;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/estoque")
@AllArgsConstructor
public class EstoqueController {
    private EstoqueServices services;

    @GetMapping
    public ResponseEntity<List<Estoque>> listar(Estoque estoque) {
        return ResponseEntity.ok().body(services.listar(estoque));
    }

    @PostMapping
    public ResponseEntity<Estoque> criar(@RequestBody EstoqueRequest estoque) {
        Estoque estoqueCriado = services.salvar(estoque);
        return ResponseEntity.created(URI.create(estoqueCriado.getId().toString())).body(estoqueCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody EstoqueRequest estoque) {
        services.atualizar(id, estoque);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        services.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
