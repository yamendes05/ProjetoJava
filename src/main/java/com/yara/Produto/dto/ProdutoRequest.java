package com.yara.Produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "O código é obrigatório")
        String codigo,

        @NotBlank(message = "A marca é obrigatória")
        String marca,

        @NotBlank(message = "O tipo é obrigatório")
        String tipo,

        @NotBlank(message = "A categoria é obrigatória")
        String categoria,

        @NotNull(message = "O preço unitário é obrigatório")
        @Positive(message = "O preço unitário deve ser maior que zero")
        BigDecimal precoUnitario,

        @NotNull(message = "O custo é obrigatório")
        @PositiveOrZero(message = "O custo deve ser maior ou igual a zero")
        BigDecimal custo,

        String obs
) {
}
