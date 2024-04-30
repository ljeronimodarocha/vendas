package br.com.github.ljeronimodarocha.vendas.domain.entity.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

/**
 * EstoqueRequest
 */
public record EstoqueRequest(@NotNull Long produtoId, @NotNull BigDecimal valor, @NotNull Integer quantidade) {

}