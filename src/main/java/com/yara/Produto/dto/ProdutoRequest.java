package com.yara.Produto.dto;

public record ProdutoRequest(String codigo, String marca, String tipo, String categoria, java.math.BigDecimal precoUnitario, java.math.BigDecimal custo, String obs) {
}
